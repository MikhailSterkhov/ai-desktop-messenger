package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.NodeObserver;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.NodeObserverConfigurable;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;

public interface KeyPressedObserver<T extends AbstractSceneForm<?>>
    extends NodeObserver<T>,
    NodeObserverConfigurable {

  void withKey(@NonNull KeyCode key);

  void observe(@NonNull KeyEvent event);
}
