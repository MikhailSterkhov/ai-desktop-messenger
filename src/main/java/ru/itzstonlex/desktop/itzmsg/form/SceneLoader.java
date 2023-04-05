package ru.itzstonlex.desktop.itzmsg.form;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable.Entry;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecaseKeys;

@RequiredArgsConstructor
public final class SceneLoader {

  private final RuntimeBlocker formsInitBlocker = new RuntimeBlocker();
  private final RuntimeBlocker redirectBlocker = new RuntimeBlocker();

  @Getter
  private final Map<Entry, AbstractSceneForm> initializedFormsMap = new HashMap<>();

  @Getter
  private final Map<Entry, Node> initializedFormsAsNodesMap = new HashMap<>();

  private final Map<Entry, Scene> loadedJavaFXScenesMap = new HashMap<>();

  @Getter
  private final Stage stage;

  @Getter
  private Entry currentSceneEntry;

  public AbstractSceneForm loadUncachedSceneForm(Entry entry) throws IOException {
    URL resourceUrl = getClass().getResource(File.separator + SceneViewTable.VIEW_FORMS_PATH + entry.getResourceView());

    FXMLLoader javafxLoader = new FXMLLoader(resourceUrl);

    Parent parent = javafxLoader.load();
    Object controller = javafxLoader.getController();

    if (!(controller instanceof AbstractSceneForm)) {
      throw new IOException("Scene " + entry.getUsageName().toUpperCase() + " controller is not instanceof from AbstractSceneForm");
    }

    AbstractSceneForm abstractSceneForm = (AbstractSceneForm) controller;
    abstractSceneForm.getUsecase().set(FormUsecaseKeys.SCENE_LOADER, this);

    abstractSceneForm.setJavafxNode(parent);

    return abstractSceneForm;
  }

  private void initializeSceneForm(Entry sceneEntry) throws IOException {
    AbstractSceneForm abstractSceneForm = loadUncachedSceneForm(sceneEntry);

    initializedFormsAsNodesMap.put(sceneEntry, abstractSceneForm.getJavafxNode());
    loadedJavaFXScenesMap.put(sceneEntry, new Scene(abstractSceneForm.getJavafxNode()));
    initializedFormsMap.put(sceneEntry, abstractSceneForm);
  }

  public void initializeForms(SceneViewTable.Entry[] entriesForLoad) throws IOException {
    formsInitBlocker.checkPrecondition();
    formsInitBlocker.block();

    for (SceneViewTable.Entry sceneViewEntry : entriesForLoad) {
      initializeSceneForm(sceneViewEntry);
    }
  }

  private Scene getJavaFXScene(SceneViewTable.Entry sceneEntry) {
    return loadedJavaFXScenesMap.get(sceneEntry);
  }

  public AbstractSceneForm getCurrentSceneForm() {
    if (currentSceneEntry == null) {
      return null;
    }
    return initializedFormsMap.get(currentSceneEntry);
  }

  public void forwardsTo(SceneViewTable.Entry sceneEntry) {
    onRedirect(sceneEntry);
    System.out.println("[SceneLoader] UI was redirected to " + sceneEntry.getUsageName() + " (" + getCurrentSceneForm().getClass() + ")");

    synchronized (redirectBlocker) {
      Scene scene = getJavaFXScene(sceneEntry);

      AbstractSceneForm abstractSceneForm = getCurrentSceneForm();
      FormUsecase usecase = abstractSceneForm.getUsecase();

      stage.setWidth(usecase.get(FormUsecaseKeys.SCENE_WIDTH, scene.getWidth()));
      stage.setHeight(usecase.get(FormUsecaseKeys.SCENE_HEIGHT, scene.getHeight()));

      stage.setResizable(!usecase.getAsFlag(FormUsecaseKeys.FRAME_RESIZABLE_DISABLE));

      stage.setScene(scene);
    }
  }

  @SuppressWarnings("ConstantConditions")
  private void onRedirect(SceneViewTable.Entry forward) {
    redirectBlocker.checkPrecondition();
    redirectBlocker.block();

    synchronized (redirectBlocker) {
      SceneViewTable.Entry backward = currentSceneEntry;

      if (backward != null) {
        getCurrentSceneForm().getUsecase().set(FormUsecaseKeys.FORWARD_SCENEFORM, forward);
      }

      currentSceneEntry = forward;

      AbstractSceneForm forwardForm = getCurrentSceneForm();

      forwardForm.getUsecase().set(FormUsecaseKeys.BACKWARD_SCENEFORM, backward);
      forwardForm.getUsecase().clearKey(FormUsecaseKeys.FORWARD_SCENEFORM);

      redirectBlocker.unblock();
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends AbstractSceneForm> T getCachedSceneForm(@NonNull Entry entry) {
    return ((T) initializedFormsMap.get(entry));
  }

  public Node getCachedSceneFormAsNode(@NonNull Entry entry) {
    return initializedFormsAsNodesMap.get(entry);
  }
}
