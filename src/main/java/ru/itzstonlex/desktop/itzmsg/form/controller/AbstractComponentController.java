package ru.itzstonlex.desktop.itzmsg.form.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.utility.RuntimeBlocker;

@RequiredArgsConstructor
public abstract class AbstractComponentController {

  private final RuntimeBlocker componentInitBlocker = new RuntimeBlocker();

  @Getter
  private final AbstractSceneForm<?> form;

  protected abstract void configureController();

  public final void initialize() {
    componentInitBlocker.checkPrecondition();
    componentInitBlocker.block();

    configureController();
  }

  public final void fireFunction(String name, Object... values) {
    AbstractSceneForm<?> abstractSceneForm = getForm();

    if (abstractSceneForm != null) {
      abstractSceneForm.fireFunction(name, values);
    }
  }

}
