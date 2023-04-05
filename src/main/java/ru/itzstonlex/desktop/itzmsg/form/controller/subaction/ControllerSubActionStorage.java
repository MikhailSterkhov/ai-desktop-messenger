package ru.itzstonlex.desktop.itzmsg.form.controller.subaction;

import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;

public interface ControllerSubActionStorage<T extends AbstractComponentController> {

  T getController();

  void setController(T controller);
}
