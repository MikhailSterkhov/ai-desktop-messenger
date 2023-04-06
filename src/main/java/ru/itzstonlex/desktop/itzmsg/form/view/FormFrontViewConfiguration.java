package ru.itzstonlex.desktop.itzmsg.form.view;

import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.itzstonlex.desktop.itzmsg.usecase.IUsecaseKeysStorage;

public interface FormFrontViewConfiguration extends IUsecaseKeysStorage {

  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode(callSuper = true)
  @FieldDefaults(level = AccessLevel.PRIVATE)
  class NodeKey<T extends Node> extends Key
  {
    Node node;

    public NodeKey(String name) {
      super(name);
    }

    public NodeKey(Class<T> type, String name) {
      this(name);
    }
  }
}
