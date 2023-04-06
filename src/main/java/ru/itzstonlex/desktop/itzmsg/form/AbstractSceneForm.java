package ru.itzstonlex.desktop.itzmsg.form;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itzstonlex.desktop.itzmsg.form.FormKeys.FormKey;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunction;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionProcessor;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontView;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public abstract class AbstractSceneForm<V extends FormFrontView<?>> {

  @ToString.Include
  @Getter
  protected final FormKey key;

  private final Map<String, FormFunctionProcessor> functionProcessorsMap = new HashMap<>();

  @Getter
  private final Set<AbstractComponentController> componentControllers = new HashSet<>();

  private final RuntimeBlocker initializationBlocker = new RuntimeBlocker();

  @Getter
  protected final FormUsecase usecase = new FormUsecase();

  @Getter
  @Setter
  private Parent javafxNode;

  @Getter
  protected V view;

  public final FormLoader getSceneLoader() {
    return usecase.get(FormUsecaseKeys.SCENE_LOADER_OBJ);
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

  public abstract V newFrontView();

  public abstract FormFunctionReleaser<?> newFunctionReleaser();

  public abstract void initializeUsecase(FormUsecase usecase);

  public abstract void initializeControllers();

  public void initializeParameters() {
    initializationBlocker.checkPrecondition();
    view = newFrontView();

    initializeUsecase(usecase);
    initializeControllers();

    initComponentsControllers();

    try {
      @SuppressWarnings("unchecked")
      FormFunctionReleaser<AbstractSceneForm<?>> functionReleaser =
          (FormFunctionReleaser<AbstractSceneForm<?>>) newFunctionReleaser();

      if (functionReleaser != null) {
        initializeControllerProcesses(functionReleaser);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    initializationBlocker.block();
  }

  @SuppressWarnings("unchecked")
  public <T extends AbstractComponentController> T getController(Class<T> controllerType) {
    return (T) componentControllers.stream()
        .filter(controller -> controller.getClass() == controllerType).findFirst()
        .orElse(null);
  }

  private void initializeControllerProcesses(FormFunctionReleaser<AbstractSceneForm<?>> formFunctionReleaser) throws IllegalAccessException {
    formFunctionReleaser.setForm(this);

    Class<?> cls = formFunctionReleaser.getClass();
    for (Method declaredMethod : cls.getDeclaredMethods()) {
      FormFunction annotation = declaredMethod.getDeclaredAnnotation(FormFunction.class);

      if (annotation != null) {
        FormFunctionProcessor processAction = (values) -> {
          try {
            declaredMethod.invoke(formFunctionReleaser, values);
          }
          catch (Exception exception) {
            exception.printStackTrace();
          }
        };

        functionProcessorsMap.put(annotation.key(), processAction);
      }
    }
  }

  public final void fireFunction(String name, Object... values) {
    String currentClassName = getClass().getName();
    FormFunctionProcessor processAction = functionProcessorsMap.get(name);

    if (processAction == null) {
      throw new NullPointerException(currentClassName + " - failed function \"" + name + "\" execution: NULL");
    }

    System.out.println("[ComponentController] Call function \"" + name + "\" for " + currentClassName);
    Platform.runLater(() -> processAction.process(values));
  }

}
