package ru.itzstonlex.desktop.chatbotmessenger.form.feed.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.view.AbstractFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.view.FormFrontViewUsecase;

public final class FeedFormFrontView extends AbstractFormFrontView<FeedFormFrontViewConfiguration> {

  public FeedFormFrontView(@NonNull AbstractSceneForm<?> form) {
    super(form, FeedFormFrontViewConfiguration.class);
  }

  public Node createMessageEmptySeparator(int height) {
    Pane messageEmptySeparator = new Pane();
    messageEmptySeparator.setPrefHeight(height);

    return messageEmptySeparator;
  }

  public Node createWrappedMessageNode(Node node) {
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
    // nothing.
  }
}
