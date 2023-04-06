package ru.itzstonlex.desktop.itzmsg.form.observer.impl;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.itzmsg.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.controller.BothMessagesReceiveController;

public class FooterButtonSendClickObserver extends AbstractMouseClickedObserver<BothMessagesReceiveController> {

  @Override
  public ApplicationFormKeys.Key getExtendedFormKey() {
    return ApplicationFormKeys.FEED;
  }

  @Override
  public void configure() {
    // nothing.
  }

  @Override
  public void observe(@NonNull MouseEvent event) {
    BothMessagesReceiveController controller = getController();

    TextField component = controller.getConfiguration()
        .getNode(BothMessagesReceiveController.MESSAGE_FIELD);

    System.out.println("Observe SendButtonObserverMouseClicked.");

    if (component.getText().trim().isEmpty()) {
      return;
    }

    controller.onMessageReceive(component.getText());
    component.clear();
  }
}
