package ru.itzstonlex.desktop.itzmsg.form;

import java.util.HashSet;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public abstract class AbstractSceneForm {

  @ToString.Include
  @Getter
  private final SceneViewTable.Entry viewEntry;

  @Getter
  private final Set<AbstractComponentController> componentControllers = new HashSet<>();

  private final RuntimeBlocker initializationBlocker = new RuntimeBlocker();

  @Getter
  private final FormUsecase usecase = new FormUsecase();

  @Getter
  @Setter
  private Parent javafxNode;

  public final SceneLoader getSceneLoader() {
    return usecase.get(FormUsecaseKeys.SCENE_LOADER);
  }

  protected final void addController(AbstractComponentController controller) {
    initializationBlocker.checkPrecondition();
    componentControllers.add(controller);
  }

  private void initComponentsControllers() {
    initializationBlocker.checkPrecondition();
    for (AbstractComponentController controller : componentControllers) {
      controller.initialize();
    }
  }

  @FXML
  void initialize() {
    initializationBlocker.checkPrecondition();

    initializeControllers();
    initializeUsecase(usecase);

    initComponentsControllers();
    initializationBlocker.block();
  }

  public abstract void initializeUsecase(FormUsecase usecase);

  public abstract void initializeControllers();
}
