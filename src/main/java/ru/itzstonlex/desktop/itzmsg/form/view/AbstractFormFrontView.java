package ru.itzstonlex.desktop.itzmsg.form.view;

import java.lang.reflect.Field;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewConfiguration.NodeKey;

public abstract class AbstractFormFrontView<T extends FormFrontViewConfiguration>
    implements FormFrontView<T> {

  private final FormFrontViewUsecase<T> usecase = new FormFrontViewUsecase<>();

  public AbstractFormFrontView(@NonNull AbstractSceneForm<?> form, @NonNull Class<T> configurationCls) {
    initialize(form, configurationCls);
  }

  private Node lookupNode(Region startNode, Set<Node> allNodesSet, String specificID) {
    Node returnValue = null;
    for (Node node : (startNode == null ? allNodesSet : startNode.getChildrenUnmodifiable())) {

      String id = node.getId();
      if (specificID.equals(id)) {
        return node;
      }

      if (returnValue == null && node instanceof Region) {
        if (node instanceof ScrollPane && ((ScrollPane) node).getContent() instanceof Region)
          returnValue = lookupNode((Region) ((ScrollPane) node).getContent(), allNodesSet, specificID);
        else
          returnValue = lookupNode((Region) node, allNodesSet, specificID);
      }
    }

    return returnValue;
  }

  @SneakyThrows
  private void fillUsecase(@NonNull Parent parent, @NonNull Class<?> configurationType) {
    Set<Node> allNodesSet = parent.lookupAll("*");

    Field[] keyFieldsArray = configurationType.getFields();

    for (Field keyField : keyFieldsArray) {
      if (!NodeKey.class.isAssignableFrom(keyField.getType()))
        continue;

      keyField.setAccessible(true);

      NodeKey<?> nodeKey = ((NodeKey<?>) keyField.get(null));
      Node node = lookupNode(null, allNodesSet, nodeKey.getName());

      if (node == null) {
        throw new NullPointerException(String.format("Node '%s' isn`t found at '%s'", nodeKey.getName(), configurationType.getName()));
      }

      usecase.set(nodeKey, node);
    }
  }

  private void initialize(@NonNull AbstractSceneForm<?> form, @NonNull Class<T> configurationCls) {
    final Parent parent = form.getJavafxNode();

    fillUsecase(parent, configurationCls);
    configureDisplay(usecase);
  }

  @Override
  public <V extends Node> V find(@NonNull NodeKey<V> nodeKey) {
    return usecase.get(nodeKey);
  }

  @Override
  public <V extends Node> void update(@NonNull NodeKey<V> nodeKey, V node) {
    usecase.set(nodeKey, node);
  }
}
