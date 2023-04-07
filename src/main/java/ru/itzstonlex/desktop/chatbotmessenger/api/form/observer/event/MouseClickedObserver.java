package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event;

import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.NodeObserver;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.NodeObserverConfigurable;

public interface MouseClickedObserver<T extends AbstractSceneForm<?>>
    extends NodeObserver<T>,
    NodeObserverConfigurable {

  void observe(@NonNull MouseEvent event);
}
