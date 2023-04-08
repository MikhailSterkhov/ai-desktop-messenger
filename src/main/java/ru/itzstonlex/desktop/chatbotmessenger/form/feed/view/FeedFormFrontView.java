package ru.itzstonlex.desktop.chatbotmessenger.form.feed.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

  public void createSuggestionBox(String text) {
    Button suggestionButton = new Button(text);

    suggestionButton.setMinHeight(Region.USE_COMPUTED_SIZE);
    suggestionButton.setMaxHeight(Region.USE_COMPUTED_SIZE);

    suggestionButton.setMinWidth(Region.USE_COMPUTED_SIZE);
    suggestionButton.setMaxWidth(Region.USE_COMPUTED_SIZE);

    suggestionButton.setPrefHeight(Region.USE_COMPUTED_SIZE);
    suggestionButton.setPrefWidth(Region.USE_COMPUTED_SIZE);

    suggestionButton.setOnAction(actionEvent -> {

      TextField inputMessageField = super.find(FeedFormFrontViewConfiguration.INPUT_MESSAGE_FIELD);
      inputMessageField.setText(text);
    });

    HBox suggestionsDisplayBox = super.find(FeedFormFrontViewConfiguration.SUGGESTIONS_DISPLAY_BOX);
    suggestionsDisplayBox.getChildren().add(suggestionButton);
  }

  @Override
  public void configureDisplay(@NonNull FormFrontViewUsecase<FeedFormFrontViewConfiguration> usecase) {
    // nothing.
  }
}
