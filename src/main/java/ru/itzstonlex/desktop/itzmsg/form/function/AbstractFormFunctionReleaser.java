package ru.itzstonlex.desktop.itzmsg.form.function;

import lombok.Getter;
import lombok.Setter;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;

public abstract class AbstractFormFunctionReleaser<T extends AbstractSceneForm<?>>
    implements FormFunctionReleaser<T> {

  @Getter
  @Setter
  private T form;
}
