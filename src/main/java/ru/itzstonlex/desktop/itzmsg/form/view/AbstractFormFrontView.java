package ru.itzstonlex.desktop.itzmsg.form.view;

import java.lang.reflect.Field;
import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewConfiguration.NodeKey;

public abstract class AbstractFormFrontView<T extends FormFrontViewConfiguration>
    implements FormFrontView<T> {

  private static final String SELECTOR_PREFIX = "#";

  private final FormFrontViewUsecase<T> usecase = new FormFrontViewUsecase<>();

  public AbstractFormFrontView(@NonNull AbstractSceneForm<?> form, @NonNull Class<T> configurationCls) {
    initialize(form, configurationCls);
  }

  @SneakyThrows
  private void fillUsecase(@NonNull Parent parent, @NonNull Class<?> configurationType) {
    Field[] keyFieldsArray = configurationType.getFields();

    for (Field keyField : keyFieldsArray) {
      if (!NodeKey.class.isAssignableFrom(keyField.getType()))
        continue;

      keyField.setAccessible(true);
      NodeKey<?> nodeKey = (NodeKey<?>) keyField.get(null);

      String nodeSpecificId = nodeKey.getName().startsWith(SELECTOR_PREFIX) ? nodeKey.getName() : SELECTOR_PREFIX + nodeKey.getName();

      Node node = parent.lookup(nodeSpecificId);
      if (node == null)
        throw new NullPointerException(String.format("Node '%s' isn`t found at '%s'", nodeSpecificId, configurationType.getName()));

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
