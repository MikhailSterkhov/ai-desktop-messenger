package ru.itzstonlex.desktop.itzmsg.form.controller.subaction;

import lombok.Getter;
import lombok.Setter;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;

public abstract class AbstractControllerSubActionStorage<T extends AbstractComponentController>
    implements ControllerSubActionStorage<T> {

  @Getter
  @Setter
  private T controller;
}
