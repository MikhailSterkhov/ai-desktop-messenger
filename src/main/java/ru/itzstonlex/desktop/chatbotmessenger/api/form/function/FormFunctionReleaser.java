package ru.itzstonlex.desktop.chatbotmessenger.api.form.function;

import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;

public interface FormFunctionReleaser<T extends AbstractSceneForm<?>> {

  T getForm();

  void setForm(T form);
}
