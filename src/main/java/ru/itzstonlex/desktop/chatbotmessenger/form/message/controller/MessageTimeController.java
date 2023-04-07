package ru.itzstonlex.desktop.chatbotmessenger.form.message.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;

public final class MessageTimeController extends AbstractComponentController<MessageForm> {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

  public MessageTimeController(MessageForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    updateCurrentTime();
  }

  public void updateCurrentTime() {
    MessageFormFrontView view = getForm().getView();

    Label label = view.find(MessageFormFromViewConfiguration.MESSAGE_TIME_LABEL);
    label.setText(DATE_FORMAT.format(System.currentTimeMillis()));
  }
}
