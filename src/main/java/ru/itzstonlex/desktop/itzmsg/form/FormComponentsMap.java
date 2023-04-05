package ru.itzstonlex.desktop.itzmsg.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import lombok.NonNull;

public final class FormComponentsMap {

  private final Map<String, Node> handlingNodesMap = new HashMap<>();

  @SuppressWarnings("unchecked")
  public <N extends Node> N getNode(@NonNull String key) {
    return (N) handlingNodesMap.get(key.toLowerCase());
  }

  public void addNode(@NonNull String key, @NonNull Node node) {
    handlingNodesMap.put(key.toLowerCase(), node);
  }

  public Collection<Node> getHandledNodes() {
    return handlingNodesMap.values();
  }
}
