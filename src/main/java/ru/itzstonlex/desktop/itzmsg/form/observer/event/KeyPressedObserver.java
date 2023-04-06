package ru.itzstonlex.desktop.itzmsg.form.observer.event;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.observer.NodeObserver;
import ru.itzstonlex.desktop.itzmsg.form.observer.NodeObserverConfigurable;

public interface KeyPressedObserver<T extends AbstractComponentController>
    extends NodeObserver<T>,
    NodeObserverConfigurable {

  void withKey(@NonNull KeyCode key);

  void observe(@NonNull KeyEvent event);
}