package ru.itzstonlex.desktop.itzmsg.form.view;

import java.util.HashMap;
import javafx.scene.Node;
import ru.itzstonlex.desktop.itzmsg.usecase.AbstractUsecaseManager;

public final class FormFrontViewUsecase<T extends FormFrontViewConfiguration>
    extends AbstractUsecaseManager<T> {

  public FormFrontViewUsecase() {
    super(new HashMap<>());
  }

  public <V extends Node> V get(FormFrontViewConfiguration.NodeKey<V> nodeKey) {
    return super.get(nodeKey);
  }
}
