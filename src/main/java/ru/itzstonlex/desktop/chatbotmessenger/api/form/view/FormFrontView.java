package ru.itzstonlex.desktop.chatbotmessenger.api.form.view;

import javafx.scene.Node;
import lombok.NonNull;

public interface FormFrontView<T extends FormFrontViewConfiguration> {

  void configureDisplay(@NonNull FormFrontViewUsecase<T> usecase);

  <V extends Node> void update(@NonNull FormFrontViewConfiguration.NodeKey<V> nodeKey, V node);

  <V extends Node> V find(@NonNull FormFrontViewConfiguration.NodeKey<V> nodeKey);
}
