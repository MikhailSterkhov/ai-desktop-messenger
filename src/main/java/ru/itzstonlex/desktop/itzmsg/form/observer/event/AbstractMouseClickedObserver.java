package ru.itzstonlex.desktop.itzmsg.form.observer.event;

import javafx.scene.Node;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.observer.AbstractNodeObserver;

public abstract class AbstractMouseClickedObserver<T extends AbstractComponentController>
    extends AbstractNodeObserver<T>
    implements MouseClickedObserver<T> {

  @Override
  public void beginObserving() {
    System.out.println("[AbstractMouseClickedObserver] begin observing");

    Node component = getComponent();

    if (component != null)
      component.setOnMouseClicked(this::observe);
  }
}
