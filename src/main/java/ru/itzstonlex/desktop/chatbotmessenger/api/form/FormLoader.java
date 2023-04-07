package ru.itzstonlex.desktop.chatbotmessenger.api.form;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nodeson.NodesonUnsafe;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.NodeObserver;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.NodeObserverConfigurable;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceClasspathScanner;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.RuntimeBlocker;

@RequiredArgsConstructor
public final class FormLoader {

  private final RuntimeBlocker formsInitBlocker = new RuntimeBlocker();
  private final RuntimeBlocker redirectBlocker = new RuntimeBlocker();

  @Getter
  private final Stage stage;

  @Getter
  private ApplicationFormKeys.Key activeApplicationFormKey;

  @Getter
  private final Map<ApplicationFormKeys.Key, Node> applicationFormsParentsMap = new HashMap<>();

  @Getter
  private final Map<ApplicationFormKeys.Key, Scene> applicationFormsScenesMap = new HashMap<>();

  @Getter
  private final Map<ApplicationFormKeys.Key, AbstractSceneForm<?>> applicationFormsMap = new HashMap<>();

  public AbstractSceneForm<?> loadUncachedForm(ApplicationFormKeys.Key key) throws IOException {
    try (Resource resource = ResourceFactory.openClasspath(ResourceGroup.JAVAFX.file(ResourceDirection.JAVAFX_MARKDOWNS, key.getName()))) {
      FXMLLoader javafxLoader = new FXMLLoader(resource.toURL());

      Parent parent = javafxLoader.load();
      Object controller = javafxLoader.getController();

      if (!(controller instanceof AbstractSceneForm)) {
        throw new IOException("Scene " + key.getName().toUpperCase() + " controller is not instanceof from AbstractSceneForm");
      }

      AbstractSceneForm<?> abstractSceneForm = ((AbstractSceneForm<?>) controller);

      abstractSceneForm.getUsecase().set(FormUsecaseKeys.SCENE_LOADER_OBJ, this);
      abstractSceneForm.setJavafxNode(parent);

      abstractSceneForm.initializeParameters();
      injectAllObservers(abstractSceneForm);

      return abstractSceneForm;
    }
  }

  private void initializeForm(ApplicationFormKeys.Key key) throws IOException {
    AbstractSceneForm<?> abstractSceneForm = loadUncachedForm(key);
    Parent parentNode = abstractSceneForm.getJavafxNode();

    Scene scene = new Scene(abstractSceneForm.getJavafxNode());

    applicationFormsScenesMap.put(key, scene);
    applicationFormsParentsMap.put(key, parentNode);
    applicationFormsMap.put(key, abstractSceneForm);
  }

  public void configureFormsCaches(ApplicationFormKeys.Key[] formKeys) throws IOException {
    formsInitBlocker.checkPrecondition();
    formsInitBlocker.block();

    for (ApplicationFormKeys.Key sceneViewFormKey : formKeys) {
      initializeForm(sceneViewFormKey);
    }
  }

  private Scene getJFXScene(ApplicationFormKeys.Key formKey) {
    return applicationFormsScenesMap.get(formKey);
  }

  public AbstractSceneForm<?> getActiveForm() {
    if (activeApplicationFormKey == null)
      return null;

    return applicationFormsMap.get(activeApplicationFormKey);
  }

  public void forwardsTo(ApplicationFormKeys.Key formKey) {
    onRedirect(formKey);
    System.out.println("[SceneLoader] UI was redirected to " + formKey.getName() + " (" + getActiveForm().getClass() + ")");

    synchronized (redirectBlocker) {
      Scene scene = getJFXScene(formKey);

      AbstractSceneForm<?> abstractSceneForm = getActiveForm();
      FormUsecase usecase = abstractSceneForm.getUsecase();

      stage.setWidth(usecase.get(FormUsecaseKeys.CUSTOM_WIDTH, scene.getWidth()));
      stage.setHeight(usecase.get(FormUsecaseKeys.CUSTOM_HEIGHT, scene.getHeight()));

      stage.setResizable(!usecase.getAsFlag(FormUsecaseKeys.FRAME_RESIZABLE_DISABLE_FLAG));

      stage.setScene(scene);
    }
  }

  @SuppressWarnings("ConstantConditions")
  private void onRedirect(ApplicationFormKeys.Key forwardKey) {
    redirectBlocker.checkPrecondition();
    redirectBlocker.block();

    synchronized (redirectBlocker) {
      ApplicationFormKeys.Key backward = activeApplicationFormKey;

      if (backward != null) {
        getActiveForm().getUsecase().set(FormUsecaseKeys.FORWARD_FORM, forwardKey);
      }

      activeApplicationFormKey = forwardKey;

      AbstractSceneForm<?> forwardForm = getActiveForm();

      forwardForm.getUsecase().set(FormUsecaseKeys.BACKWARD_FORM, backward);
      forwardForm.getUsecase().clearKey(FormUsecaseKeys.FORWARD_FORM);

      redirectBlocker.unblock();
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends AbstractSceneForm<?>> T getCachedForm(@NonNull ApplicationFormKeys.Key key) {
    return ((T) applicationFormsMap.get(key));
  }

  @SneakyThrows
  private void registerObservers(AbstractSceneForm<?> form, Collection<?> injectInstances, Set<NodeObserver<AbstractSceneForm<?>>> observers) {
    for (Object instanceObj : injectInstances) {
      Class<?> controllerCls = instanceObj.getClass();

      for (Field field : controllerCls.getDeclaredFields()) {
        ObserveBy annotation = field.getDeclaredAnnotation(ObserveBy.class);
        if (annotation == null || !Node.class.isAssignableFrom(field.getType()))
          continue;

        Class<? extends NodeObserver<?>>[] observersArray = annotation.value();
        List<Class<? extends NodeObserver<?>>> observersList = Arrays.asList(observersArray);

        field.setAccessible(true);

        Node node = ((Node) field.get(instanceObj));
        observers.stream()
            .filter(observer -> observersList.contains(observer.getClass()))
            .findFirst()
            .ifPresent(
                (nodeObserver) -> {
                  nodeObserver.setForm(form);
                  nodeObserver.setComponent(node);

                  if (nodeObserver instanceof NodeObserverConfigurable)
                    ((NodeObserverConfigurable) nodeObserver).configure();

                  field.setAccessible(false);
                  nodeObserver.beginObserving();
                });
      }
    }
  }

  public void injectAllObservers(AbstractSceneForm<?> form) {
    Map<ApplicationFormKeys.Key, Set<NodeObserver<AbstractSceneForm<?>>>> observersMap = loadObservers();
    Set<NodeObserver<AbstractSceneForm<?>>> observerSet = observersMap.get(form.key);

    registerObservers(form, form.getComponentControllers(), observerSet);

    registerObservers(form, Collections.singletonList(form), observerSet);
    registerObservers(form, Collections.singletonList(form.getView()), observerSet);
  }

  @SuppressWarnings("unchecked")
  @SneakyThrows
  private Map<ApplicationFormKeys.Key, Set<NodeObserver<AbstractSceneForm<?>>>> loadObservers() {
    Map<ApplicationFormKeys.Key, Set<NodeObserver<AbstractSceneForm<?>>>> map = new HashMap<>();

    ResourceClasspathScanner resourceClasspathScanner = new ResourceClasspathScanner();
    Set<Class<?>> allClassesUsingClassLoader = resourceClasspathScanner.findAllClassesUsingClassLoader(
        "ru.itzstonlex.desktop.chatbotmessenger.observer"
    );

    for (Class<?> cls : allClassesUsingClassLoader) {
      if (NodeObserver.class.isAssignableFrom(cls)) {

        NodeObserver<AbstractSceneForm<?>> observer = (NodeObserver<AbstractSceneForm<?>>) NodesonUnsafe.allocate(cls);
        Set<NodeObserver<AbstractSceneForm<?>>> observersCacheSet = map.get(observer.getApplicationFormKey());

        if (observersCacheSet == null) {
          observersCacheSet = new HashSet<>();
        }

        observersCacheSet.add(observer);

        map.put(observer.getApplicationFormKey(), observersCacheSet);
      }
    }

    return map;
  }
}
