package ru.itzstonlex.desktop.chatbotmessenger.form.message.controller;

import javafx.scene.control.Label;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFrontView;

public final class MessageTextController extends AbstractComponentController<MessageForm> {

  public static final String EMPTY_MSG = "";

  public MessageTextController(MessageForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    updateMessageText(EMPTY_MSG);
  }

  public void updateMessageText(@NonNull String text) {
    MessageFormFrontView view = getForm().getView();

    Label label = view.find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);
    label.setText(text);
  }
}
