package ru.itzstonlex.desktop.itzmsg.loading;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.SceneLoader;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable.Entry;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.NodeObserver;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.NodeObserverConfigurable;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.ObserveBy;
import ru.itzstonlex.desktop.itzmsg.utility.ClasspathScanner;

@RequiredArgsConstructor
public class LoadingJavaFXController {

  private final Stage stage;
  private SceneLoader sceneLoader;

  @FXML
  private Label descriptionOfLoading;

  @FXML
  void initialize() {
    sceneLoader = new SceneLoader(stage);
    CompletableFuture.runAsync(() -> {

      System.out.println("[Loading] Run asynchronous application parts loading...");
      LoadingPartResult result = configureApplicationForms();

      if (result == LoadingPartResult.SUCCESS) {
        setDescriptionText("\n[Done]");

        System.out.println("[Loading] Application parts was successful loaded");
        System.out.println("[Loading] Redirection to feedback page...");

        try {
          Thread.sleep(2500L);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }

        Platform.runLater(() -> sceneLoader.forwardsTo(constructSceneViewTable()[0]));
      }
    });
  }

  private LoadingPartResult configureApplicationForms() {
    CompletableFuture<LoadingPartResult> atomicResult = new CompletableFuture<>();
    Entry[] entries = constructSceneViewTable();

    try {
      initializeApplicationForms(entries);
      atomicResult.complete(LoadingPartResult.SUCCESS);
    }
    catch (IOException exception) {
      shorError(exception);

      atomicResult.complete(LoadingPartResult.ERROR);
    }

    return atomicResult.join();
  }

  private Entry[] constructSceneViewTable() {
    return new Entry[]
        {
            SceneViewTable.FEED,
            SceneViewTable.MESSAGE,
        };
  }

  private void shorError(Exception exception) {
    System.out.println("[Loading] Error: " + exception);
    exception.printStackTrace();

    Platform.runLater(() -> {

      descriptionOfLoading.setText("internal error: " + exception.getClass() + " - " + exception.getMessage());
      descriptionOfLoading.setTextFill(Color.RED);
    });
  }

  private void setDescriptionText(String text) {
    Platform.runLater(() -> descriptionOfLoading.setText(
        descriptionOfLoading.getText().concat("\n") + text));
  }

  private void initializeApplicationForms(Entry[] entries) throws IOException {
    sceneLoader.initializeForms(entries);

    Map<Entry, Set<NodeObserver<AbstractComponentController>>> map = loadAllObservers();
    sceneLoader.getInitializedFormsMap().forEach((view, abstractSceneForm) -> {

      Set<NodeObserver<AbstractComponentController>> observerSet = map.get(view);
      initForm(abstractSceneForm, observerSet);
    });
  }

  @SneakyThrows
  private void initForm(AbstractSceneForm form, Set<NodeObserver<AbstractComponentController>> observers) {
    Class<? extends AbstractSceneForm> formCls = form.getClass();

    System.out.println("[Loading] " + formCls);
    setDescriptionText("load form class: " + formCls.getName());

    Thread.sleep(2000);

    initFormObservers(form, observers);
  }

  @SneakyThrows
  private void initFormObservers(AbstractSceneForm form, Set<NodeObserver<AbstractComponentController>> observers) {
    for (AbstractComponentController controller : form.getComponentControllers()) {
      Class<? extends AbstractComponentController> controllerCls = controller.getClass();

      for (Field field : controllerCls.getDeclaredFields()) {
        ObserveBy annotation = field.getDeclaredAnnotation(ObserveBy.class);
        if (annotation == null || !Node.class.isAssignableFrom(field.getType()))
          continue;

        Class<? extends NodeObserver<?>>[] observersArray = annotation.value();
        List<Class<? extends NodeObserver<?>>> observersList = Arrays.asList(observersArray);

        field.setAccessible(true);

        Node node = ((Node) field.get(controller));
        observers.stream()
            .filter(observer -> observersList.contains(observer.getClass()))
            .findFirst()
            .ifPresent(
                (nodeObserver) -> {
                  nodeObserver.setController(controller);
                  nodeObserver.setComponent(node);

                  if (nodeObserver instanceof NodeObserverConfigurable) {
                    ((NodeObserverConfigurable) nodeObserver).configure();
                  }

                  field.setAccessible(false);
                  nodeObserver.beginObserving();
                });
      }
    }
  }

  @SuppressWarnings("unchecked")
  @SneakyThrows
  private Map<Entry, Set<NodeObserver<AbstractComponentController>>> loadAllObservers() {
    Map<Entry, Set<NodeObserver<AbstractComponentController>>> map = new HashMap<>();
    Set<Class<?>> allClassesUsingClassLoader = ClasspathScanner.findAllClassesUsingClassLoader(
        "ru.itzstonlex.desktop.itzmsg.form.controller.observer.impl");

    for (Class<?> cls : allClassesUsingClassLoader) {
      if (NodeObserver.class.isAssignableFrom(cls)) {

        NodeObserver<AbstractComponentController> observer = (NodeObserver<AbstractComponentController>) cls.getConstructor().newInstance();
        Set<NodeObserver<AbstractComponentController>> observersSet = map.get(observer.getView());

        if (observersSet == null) {
          observersSet = new HashSet<>();
        }

        observersSet.add(observer);
        map.put(observer.getView(), observersSet);
      }
    }

    return map;
  }
}
