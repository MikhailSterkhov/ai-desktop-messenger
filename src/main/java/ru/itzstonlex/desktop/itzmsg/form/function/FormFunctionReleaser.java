package ru.itzstonlex.desktop.itzmsg.form.function;

import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;

public interface FormFunctionReleaser<T extends AbstractSceneForm> {

  T getForm();

  void setForm(T form);
}
