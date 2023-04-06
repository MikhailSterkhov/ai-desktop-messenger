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
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.itzmsg.utility.ResourcesUtils;
import ru.itzstonlex.desktop.itzmsg.utility.ResourcesUtils.ResourcesDirection;
import ru.itzstonlex.desktop.itzmsg.utility.ResourcesUtils.ResourcesGroup;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

@RequiredArgsConstructor
public final class FormLoader {

  private final RuntimeBlocker formsInitBlocker = new RuntimeBlocker();
  private final RuntimeBlocker redirectBlocker = new RuntimeBlocker();

  @Getter
  private final Map<ApplicationFormKeys.Key, AbstractSceneForm<?>> initializedFormsMap = new HashMap<>();

  @Getter
  private final Map<ApplicationFormKeys.Key, Node> initializedFormsAsNodesMap = new HashMap<>();

  private final Map<ApplicationFormKeys.Key, Scene> loadedJavaFXScenesMap = new HashMap<>();

  @Getter
  private final Stage stage;

  @Getter
  private ApplicationFormKeys.Key activeApplicationFormKey;

  public AbstractSceneForm<?> loadUncachedForm(ApplicationFormKeys.Key key) throws IOException {
    FXMLLoader javafxLoader = new FXMLLoader(
        ResourcesUtils.toClasspathResourceUrl(
            ResourcesUtils.createResourcePath(ResourcesGroup.JAVAFX, ResourcesDirection.JAVAFX_MARKDOWNS, key.getName())
        )
    );

    Parent parent = javafxLoader.load();
    Object controller = javafxLoader.getController();

    if (!(controller instanceof AbstractSceneForm)) {
      throw new IOException("Scene " + key.getName().toUpperCase() + " controller is not instanceof from AbstractSceneForm");
    }

    AbstractSceneForm<?> abstractSceneForm = ((AbstractSceneForm<?>) controller);

    abstractSceneForm.getUsecase().set(FormUsecaseKeys.SCENE_LOADER_OBJ, this);
    abstractSceneForm.setJavafxNode(parent);

    abstractSceneForm.initializeParameters();
    return abstractSceneForm;
  }

  private void cacheForm(ApplicationFormKeys.Key key) throws IOException {
    AbstractSceneForm<?> abstractSceneForm = loadUncachedForm(key);

    initializedFormsAsNodesMap.put(key, abstractSceneForm.getJavafxNode());
    loadedJavaFXScenesMap.put(key, new Scene(abstractSceneForm.getJavafxNode()));
    initializedFormsMap.put(key, abstractSceneForm);
  }

  public void configureFormsCaches(ApplicationFormKeys.Key[] formKeys) throws IOException {
    formsInitBlocker.checkPrecondition();
    formsInitBlocker.block();

    for (ApplicationFormKeys.Key sceneViewFormKey : formKeys) {
      cacheForm(sceneViewFormKey);
    }
  }

  private Scene getJFXScene(ApplicationFormKeys.Key formKey) {
    return loadedJavaFXScenesMap.get(formKey);
  }

  public AbstractSceneForm<?> getActiveForm() {
    if (activeApplicationFormKey == null)
      return null;

    return initializedFormsMap.get(activeApplicationFormKey);
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
    return ((T) initializedFormsMap.get(key));
  }
}
