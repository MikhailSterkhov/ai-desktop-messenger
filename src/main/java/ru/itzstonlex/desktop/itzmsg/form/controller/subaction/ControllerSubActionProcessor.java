package ru.itzstonlex.desktop.itzmsg.form.controller.subaction;

@FunctionalInterface
public interface ControllerSubActionProcessor {

  void process(Object... values);
}
