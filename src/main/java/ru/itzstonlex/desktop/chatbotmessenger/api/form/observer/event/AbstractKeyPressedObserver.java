package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.AbstractNodeObserver;

public abstract class AbstractKeyPressedObserver<T extends AbstractSceneForm<?>>
    extends AbstractNodeObserver<T>
    implements KeyPressedObserver<T> {

  private Set<KeyCode> awaitKeysList;

  public final boolean preconditionPressedKeys(@NonNull KeyEvent event) {
    return awaitKeysList.contains(event.getCode());
  }

  @Override
  public final void withKey(@NonNull KeyCode key) {
    if (awaitKeysList == null)
      awaitKeysList = new HashSet<>();

    awaitKeysList.add(key);
  }

  @Override
  public void beginObserving() {
    log(MESSAGE_OBSERVE_BEGIN);

    Node component = getComponent();
    component.setOnKeyPressed(keyEvent -> {

      if (preconditionPressedKeys(keyEvent))
        observe(keyEvent);
    });
  }

}
