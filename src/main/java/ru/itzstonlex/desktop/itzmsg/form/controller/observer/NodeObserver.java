package ru.itzstonlex.desktop.itzmsg.form.controller.observer;

import javafx.scene.Node;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable;

public interface NodeObserver<T extends AbstractComponentController> {

  void beginObserving();

  SceneViewTable.Entry getView();

  T getController();

  void setController(T controller);

  Node getComponent();

  void setComponent(@NonNull Node node);
}
