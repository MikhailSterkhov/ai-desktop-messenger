package ru.itzstonlex.desktop.itzmsg.form.type.feed.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.view.AbstractFormFrontView;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewUsecase;

public final class FeedFormFrontView extends AbstractFormFrontView<FeedFormFrontViewConfiguration> {

  @Getter
  private Pane messageEmptySeparator;

  public FeedFormFrontView(@NonNull AbstractSceneForm<?> form) {
    super(form, FeedFormFrontViewConfiguration.class);
  }

  private void createMessageEmptySeparator() {
    messageEmptySeparator = new Pane();
    messageEmptySeparator.setPrefHeight(10);
  }

  public Node wrapMessageNode(Node node) {
    Pane wrapper = new Pane();

    wrapper.setMinHeight(50);
    wrapper.setPrefHeight(Region.USE_COMPUTED_SIZE);
    wrapper.setMaxHeight(Region.USE_COMPUTED_SIZE);

    wrapper.setNodeOrientation(node.getNodeOrientation());
    wrapper.getChildren().add(node);

    return wrapper;
  }

  @Override
  public void configureDisplay(@NonNull FormFrontViewUsecase<FeedFormFrontViewConfiguration> usecase) {
    createMessageEmptySeparator();
  }
}
