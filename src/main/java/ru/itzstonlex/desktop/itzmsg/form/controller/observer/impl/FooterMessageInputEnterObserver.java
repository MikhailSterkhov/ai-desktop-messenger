package ru.itzstonlex.desktop.itzmsg.form.controller.observer.impl;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.FormKeys.FormKey;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.event.AbstractKeyPressedObserver;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.itzmsg.form.FormKeys;

public class FooterMessageInputEnterObserver extends AbstractKeyPressedObserver<BothMessagesReceiveController> {

  @Override
  public FormKeys.FormKey getView() {
    return FormKeys.FEED;
  }

  @Override
  public void configure() {
    withKey(KeyCode.ENTER);
  }

  @Override
  public void observe(@NonNull KeyEvent event) {
    BothMessagesReceiveController controller = getController();
    TextField component = ((TextField) getComponent());

    System.out.println("Observe MessageFieldObserverKeyPressed.");

    if (event.isShiftDown()) {
      component.setText(component.getText().concat("\n"));
      return;
    }

    if (component.getText().trim().isEmpty()) {
      return;
    }

    controller.onMessageReceive(component.getText());
    component.clear();
  }
}
