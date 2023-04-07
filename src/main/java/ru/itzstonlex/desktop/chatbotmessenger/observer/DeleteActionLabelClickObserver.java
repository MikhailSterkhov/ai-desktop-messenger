package ru.itzstonlex.desktop.chatbotmessenger.observer;

import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.AbstractNodeObserver;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.controller.DeleteActionLabelController;

public class DeleteActionLabelClickObserver extends AbstractMouseClickedObserver<MessageForm> {

  private DeleteActionLabelController controller;

  @Override
  public void observe(@NonNull MouseEvent event) {
    log(AbstractNodeObserver.MESSAGE_OBSERVE_PROCESS);

    if (controller.isDeleted()) {
      controller.restoreMessageDeleted();
    } else {
      controller.deleteMessage();
    }

    controller.updateLabelDisplay();
  }

  @Override
  public void configure() {
    controller = getForm().getController(DeleteActionLabelController.class);
  }

  @Override
  public ApplicationFormKeys.Key getApplicationFormKey() {
    return ApplicationFormKeys.MESSAGE;
  }
}
