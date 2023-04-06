package ru.itzstonlex.desktop.itzmsg.form.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionProcessor;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

@RequiredArgsConstructor
public abstract class AbstractComponentController {

  private final RuntimeBlocker componentInitBlocker = new RuntimeBlocker();

  @Getter
  private final ControllerConfiguration configuration = new ControllerConfiguration();

  @Getter
  private final AbstractSceneForm<?> form;

  public final AbstractComponentController with(@NonNull String key, @NonNull Node node) {
    configuration.addNode(key, node);
    return this;
  }

  protected abstract void initNodes(@NonNull ControllerConfiguration configuration);

  protected abstract void configureController();

  public final void initialize() {
    componentInitBlocker.checkPrecondition();
    componentInitBlocker.block();

    initNodes(configuration);
    configureController();
  }

  public final void fireFunction(String name, Object... values) {
    AbstractSceneForm<?> abstractSceneForm = getForm();

    if (abstractSceneForm != null) {
      abstractSceneForm.fireFunction(name, values);
    }
  }

}
