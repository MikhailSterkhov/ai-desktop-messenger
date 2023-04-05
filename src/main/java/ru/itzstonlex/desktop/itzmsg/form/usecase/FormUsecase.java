package ru.itzstonlex.desktop.itzmsg.form.usecase;

import java.util.HashMap;
import ru.itzstonlex.desktop.itzmsg.usecase.AbstractUsecaseManager;

public final class FormUsecase
    extends AbstractUsecaseManager<FormUsecaseKeys> {

  public FormUsecase() {
    super(new HashMap<>());
  }
}
