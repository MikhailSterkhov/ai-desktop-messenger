package ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase;

import java.util.HashMap;
import ru.itzstonlex.desktop.chatbotmessenger.api.usecase.AbstractUsecaseManager;

public final class FormUsecase
    extends AbstractUsecaseManager<FormUsecaseKeys> {

  public FormUsecase() {
    super(new HashMap<>());
  }
}
