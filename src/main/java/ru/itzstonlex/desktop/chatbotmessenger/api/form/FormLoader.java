package ru.itzstonlex.desktop.chatbotmessenger.api.form;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserverLoader;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.RuntimeBlocker;

@RequiredArgsConstructor
public final class FormLoader {

  private final ObserverLoader observerLoader = new ObserverLoader();

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
      observerLoader.injectAllObservers(abstractSceneForm);

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
}
