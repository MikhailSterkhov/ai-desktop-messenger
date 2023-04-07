package ru.itzstonlex.desktop.chatbotmessenger.api.form.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.RuntimeBlocker;

@RequiredArgsConstructor
public abstract class AbstractComponentController<F extends AbstractSceneForm<?>> {

  private final RuntimeBlocker componentInitBlocker = new RuntimeBlocker();

  @Getter
  private final F form;

  protected abstract void configureController();

  public final void initialize() {
    componentInitBlocker.checkPrecondition();
    componentInitBlocker.block();

    configureController();
  }

  public final void fireFunction(String name, Object... values) {
    form.fireFunction(name, values);
  }

}
