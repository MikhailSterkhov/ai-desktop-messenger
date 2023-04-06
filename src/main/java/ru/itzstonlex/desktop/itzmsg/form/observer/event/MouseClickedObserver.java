package ru.itzstonlex.desktop.itzmsg.form.observer.event;

import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.observer.NodeObserver;
import ru.itzstonlex.desktop.itzmsg.form.observer.NodeObserverConfigurable;

public interface MouseClickedObserver<T extends AbstractComponentController>
    extends NodeObserver<T>,
    NodeObserverConfigurable {

  void observe(@NonNull MouseEvent event);
}
