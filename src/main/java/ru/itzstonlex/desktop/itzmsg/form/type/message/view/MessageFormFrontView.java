package ru.itzstonlex.desktop.itzmsg.form.type.message.view;

import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.view.AbstractFormFrontView;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewUsecase;

public final class MessageFormFrontView extends AbstractFormFrontView<MessageFormFromViewConfiguration> {

  public MessageFormFrontView(@NonNull AbstractSceneForm<?> form) {
    super(form, MessageFormFromViewConfiguration.class);
  }

  @Override
  public void configureDisplay(@NonNull FormFrontViewUsecase<MessageFormFromViewConfiguration> usecase) {
    // nothing.
  }
}
