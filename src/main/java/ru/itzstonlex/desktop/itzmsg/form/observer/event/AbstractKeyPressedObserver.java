package ru.itzstonlex.desktop.itzmsg.form.observer.event;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.observer.AbstractNodeObserver;

public abstract class AbstractKeyPressedObserver<T extends AbstractComponentController>
    extends AbstractNodeObserver<T>
    implements KeyPressedObserver<T> {

  private final Set<KeyCode> awaitKeysList = new HashSet<>();

  public final boolean preconditionPressedKeys(@NonNull KeyEvent event) {
    return awaitKeysList.contains(event.getCode());
  }

  @Override
  public final void withKey(@NonNull KeyCode key) {
    awaitKeysList.add(key);
  }

  @Override
  public void beginObserving() {
    System.out.println("[AbstractKeyPressedObserver] begin observing");

    Node component = getComponent();

    if (component != null) {
      component.setOnKeyPressed(keyEvent -> {

        if (preconditionPressedKeys(keyEvent))
          observe(keyEvent);
      });
    }
  }

}
