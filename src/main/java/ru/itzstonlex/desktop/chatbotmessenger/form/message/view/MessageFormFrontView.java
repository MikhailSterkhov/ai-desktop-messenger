package ru.itzstonlex.desktop.chatbotmessenger.form.message.view;

import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.view.AbstractFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.view.FormFrontViewUsecase;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;

public final class MessageFormFrontView extends AbstractFormFrontView<MessageFormFromViewConfiguration> {

  public MessageFormFrontView(@NonNull AbstractSceneForm<?> form) {
    super(form, MessageFormFromViewConfiguration.class);
  }

  @Override
  public void configureDisplay(@NonNull FormFrontViewUsecase<MessageFormFromViewConfiguration> usecase) {
    // nothing.
  }
}
