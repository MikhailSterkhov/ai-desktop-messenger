package ru.itzstonlex.desktop.chatbotmessenger.observer;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.AbstractNodeObserver;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.controller.CopyActionLabelController;
import ru.itzstonlex.desktop.chatbotmessenger.api.usecase.IUsecaseKeysStorage.Key;

public class CopyActionLabelClickObserver extends AbstractMouseClickedObserver<MessageForm> {

  private CopyActionLabelController controller;

  @Override
  public void observe(@NonNull MouseEvent event) {
    log(AbstractNodeObserver.MESSAGE_OBSERVE_PROCESS);

    if (getComponent().getCursor() == Cursor.HAND) {

      controller.copyMessageToBuffer();
      controller.changeToCopiedStatus();

      controller.resetMouseClickedEvent();
    }
  }

  @Override
  public void configure() {
    controller = getForm().getController(CopyActionLabelController.class);
  }

  @Override
  public Key getApplicationFormKey() {
    return ApplicationFormKeys.MESSAGE;
  }
}
