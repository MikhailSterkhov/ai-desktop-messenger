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
import ru.itzstonlex.desktop.itzmsg.form.FormKeys.FormKey;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

@RequiredArgsConstructor
public final class FormLoader {

  private final RuntimeBlocker formsInitBlocker = new RuntimeBlocker();
  private final RuntimeBlocker redirectBlocker = new RuntimeBlocker();

  @Getter
  private final Map<FormKey, AbstractSceneForm> initializedFormsMap = new HashMap<>();

  @Getter
  private final Map<FormKey, Node> initializedFormsAsNodesMap = new HashMap<>();

  private final Map<FormKey, Scene> loadedJavaFXScenesMap = new HashMap<>();

  @Getter
  private final Stage stage;

  @Getter
  private FormKey currentFormKey;

  public AbstractSceneForm loadUncachedForm(FormKey formKey) throws IOException {
    URL resourceUrl = getClass().getResource(File.separator + FormKeys.VIEW_FORMS_PATH + formKey.getResourceFile());

    FXMLLoader javafxLoader = new FXMLLoader(resourceUrl);

    Parent parent = javafxLoader.load();
    Object controller = javafxLoader.getController();

    if (!(controller instanceof AbstractSceneForm)) {
      throw new IOException("Scene " + formKey.getName().toUpperCase() + " controller is not instanceof from AbstractSceneForm");
    }

    AbstractSceneForm abstractSceneForm = (AbstractSceneForm) controller;
    abstractSceneForm.getUsecase().set(FormUsecaseKeys.SCENE_LOADER_OBJ, this);

    abstractSceneForm.setJavafxNode(parent);

    return abstractSceneForm;
  }

  private void cacheForm(FormKey formKey) throws IOException {
    AbstractSceneForm abstractSceneForm = loadUncachedForm(formKey);

    initializedFormsAsNodesMap.put(formKey, abstractSceneForm.getJavafxNode());
    loadedJavaFXScenesMap.put(formKey, new Scene(abstractSceneForm.getJavafxNode()));
    initializedFormsMap.put(formKey, abstractSceneForm);
  }

  public void cacheForms(FormKey[] formKeys) throws IOException {
    formsInitBlocker.checkPrecondition();
    formsInitBlocker.block();

    for (FormKey sceneViewFormKey : formKeys) {
      cacheForm(sceneViewFormKey);
    }
  }

  private Scene getJavaFXScene(FormKey formKey) {
    return loadedJavaFXScenesMap.get(formKey);
  }

  public AbstractSceneForm getActiveForm() {
    if (currentFormKey == null)
      return null;

    return initializedFormsMap.get(currentFormKey);
  }

  public void forwardsTo(FormKey formKey) {
    onRedirect(formKey);
    System.out.println("[SceneLoader] UI was redirected to " + formKey.getName() + " (" + getActiveForm().getClass() + ")");

    synchronized (redirectBlocker) {
      Scene scene = getJavaFXScene(formKey);

      AbstractSceneForm abstractSceneForm = getActiveForm();
      FormUsecase usecase = abstractSceneForm.getUsecase();

      stage.setWidth(usecase.get(FormUsecaseKeys.CUSTOM_WIDTH, scene.getWidth()));
      stage.setHeight(usecase.get(FormUsecaseKeys.CUSTOM_HEIGHT, scene.getHeight()));

      stage.setResizable(!usecase.getAsFlag(FormUsecaseKeys.FRAME_RESIZABLE_DISABLE_FLAG));

      stage.setScene(scene);
    }
  }

  @SuppressWarnings("ConstantConditions")
  private void onRedirect(FormKey forward) {
    redirectBlocker.checkPrecondition();
    redirectBlocker.block();

    synchronized (redirectBlocker) {
      FormKey backward = currentFormKey;

      if (backward != null) {
        getActiveForm().getUsecase().set(FormUsecaseKeys.FORWARD_FORM, forward);
      }

      currentFormKey = forward;

      AbstractSceneForm forwardForm = getActiveForm();

      forwardForm.getUsecase().set(FormUsecaseKeys.BACKWARD_FORM, backward);
      forwardForm.getUsecase().clearKey(FormUsecaseKeys.FORWARD_FORM);

      redirectBlocker.unblock();
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends AbstractSceneForm> T getCachedForm(@NonNull FormKey formKey) {
    return ((T) initializedFormsMap.get(formKey));
  }
}
