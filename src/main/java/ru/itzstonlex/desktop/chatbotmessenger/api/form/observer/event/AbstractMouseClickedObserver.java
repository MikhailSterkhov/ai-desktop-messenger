package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event;

import javafx.scene.Node;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.AbstractNodeObserver;

public abstract class AbstractMouseClickedObserver<T extends AbstractSceneForm<?>>
    extends AbstractNodeObserver<T>
    implements MouseClickedObserver<T> {

  @Override
  public void beginObserving() {
    log(MESSAGE_OBSERVE_BEGIN);

    Node component = getComponent();
    component.setOnMouseClicked(this::observe);
  }
}
