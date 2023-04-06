package ru.itzstonlex.desktop.itzmsg.form.view;

import javafx.scene.Node;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewConfiguration.NodeKey;

public interface FormFrontView<T extends FormFrontViewConfiguration> {

  void configureDisplay(@NonNull FormFrontViewUsecase<T> usecase);

  <V extends Node> void update(@NonNull NodeKey<V> nodeKey, V node);

  <V extends Node> V find(@NonNull FormFrontViewConfiguration.NodeKey<V> nodeKey);
}
