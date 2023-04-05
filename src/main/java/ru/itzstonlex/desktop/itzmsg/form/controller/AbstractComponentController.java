package ru.itzstonlex.desktop.itzmsg.form.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubAction;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubActionProcessor;
import ru.itzstonlex.desktop.itzmsg.form.FormComponentsMap;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubActionStorage;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

public abstract class AbstractComponentController {

  private final Map<String, ControllerSubActionProcessor> subactionsProcesses = new HashMap<>();

  private final RuntimeBlocker componentInitBlocker = new RuntimeBlocker();

  @Getter
  private final FormComponentsMap formComponentsMap = new FormComponentsMap();

  public final AbstractComponentController with(@NonNull String key, @NonNull Node node) {
    formComponentsMap.addNode(key, node);
    return this;
  }

  protected abstract ControllerSubActionStorage<?> getSubActionsStorage();

  protected abstract void initNodes(@NonNull FormComponentsMap map);

  protected abstract void configureController();

  public final void initialize() {
    componentInitBlocker.checkPrecondition();
    componentInitBlocker.block();

    initNodes(formComponentsMap);
    configureController();

    try {
      ControllerSubActionStorage<?> subActionsStorage = getSubActionsStorage();
      if (subActionsStorage != null) {
        initializeControllerProcesses(subActionsStorage);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeControllerProcesses(ControllerSubActionStorage<?> controllerSubActionStorage) throws IllegalAccessException {
    @SuppressWarnings("unchecked") ControllerSubActionStorage<AbstractComponentController> subActionsStorage
        = (ControllerSubActionStorage<AbstractComponentController>) controllerSubActionStorage;

    subActionsStorage.setController(this);

    Class<?> cls = subActionsStorage.getClass();
    for (Method declaredMethod : cls.getDeclaredMethods()) {
      ControllerSubAction annotation = declaredMethod.getDeclaredAnnotation(ControllerSubAction.class);

      if (annotation != null) {
        ControllerSubActionProcessor processAction = (values) -> {
          try {
            declaredMethod.invoke(subActionsStorage, values);
          }
          catch (Exception exception) {
            exception.printStackTrace();
          }
        };

        subactionsProcesses.put(annotation.key(), processAction);
      }
    }
  }

  public final void callSubaction(String name, Object... values) {
    ControllerSubActionProcessor processAction = subactionsProcesses.get(name);
    if (processAction == null) {
      throw new IllegalArgumentException(getClass() + " - failed sub-action \"" + name + "\" execution: process is not found!");
    }

    System.out.println("[ComponentController] Call internal sub-action \"" + name + "\" for " + getClass().getName());
    Platform.runLater(() -> processAction.process(values));
  }

}
