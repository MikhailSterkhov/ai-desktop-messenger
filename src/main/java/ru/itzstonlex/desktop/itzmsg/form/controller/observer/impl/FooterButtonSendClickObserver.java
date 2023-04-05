package ru.itzstonlex.desktop.itzmsg.form.controller.observer.impl;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.BothMessagesReceiveController;

public class FooterButtonSendClickObserver extends AbstractMouseClickedObserver<BothMessagesReceiveController> {

  @Override
  public SceneViewTable.Entry getView() {
    return SceneViewTable.FEED;
  }

  @Override
  public void configure() {
    // nothing.
  }

  @Override
  public void observe(@NonNull MouseEvent event) {
    BothMessagesReceiveController controller = getController();
    TextField component = controller.getFormComponentsMap().getNode(BothMessagesReceiveController.MESSAGE_FIELD);

    System.out.println("Observe SendButtonObserverMouseClicked.");

    if (component.getText().trim().isEmpty()) {
      return;
    }

    controller.onMessageReceive(component.getText());
    component.clear();
  }
}
