package ru.itzstonlex.desktop.itzmsg.form.controller.observer;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;

/**
 * Override based {@link NodeObserver}
 * interface methods.
 */
public abstract class AbstractNodeObserver<T extends AbstractComponentController>
    implements NodeObserver<T> {

  @Getter
  @Setter
  private T controller;

  @Getter
  @Setter
  private Node component;
}
