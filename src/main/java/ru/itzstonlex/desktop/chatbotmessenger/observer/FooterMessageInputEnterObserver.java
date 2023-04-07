package ru.itzstonlex.desktop.chatbotmessenger.observer;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractKeyPressedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.BothMessagesReceiveController;

public class FooterMessageInputEnterObserver extends AbstractKeyPressedObserver<FeedForm> {

  private BothMessagesReceiveController controller;
  private TextField textField;

  @Override
  public void observe(@NonNull KeyEvent event) {
    log(MESSAGE_OBSERVE_PROCESS);

    String text = textField.getText();
    if (event.isShiftDown() || text.trim().isEmpty())
      return;

    controller.onMessageReceive(text);
    textField.clear();
  }

  @Override
  public void configure() {
    withKey(KeyCode.ENTER);

    controller = getForm().getController(BothMessagesReceiveController.class);
    textField = ((TextField) getComponent());
  }

  @Override
  public ApplicationFormKeys.Key getApplicationFormKey() {
    return ApplicationFormKeys.FEED;
  }
}
