package ru.itzstonlex.desktop.chatbotmessenger.observer;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;

public class FooterButtonSendClickObserver extends AbstractMouseClickedObserver<FeedForm> {

  private BothMessagesReceiveController controller;
  private TextField textField;

  @Override
  public void observe(@NonNull MouseEvent event) {
    log(MESSAGE_OBSERVE_PROCESS);

    String text = textField.getText();

    if (text.trim().isEmpty())
      return;

    controller.onMessageReceive(text);
    textField.clear();
  }

  @Override
  public void configure() {
    controller = getForm().getController(BothMessagesReceiveController.class);
    textField = getForm().getView().find(FeedFormFrontViewConfiguration.INPUT_MESSAGE_FIELD);
  }

  @Override
  public ApplicationFormKeys.Key getApplicationFormKey() {
    return ApplicationFormKeys.FEED;
  }
}
