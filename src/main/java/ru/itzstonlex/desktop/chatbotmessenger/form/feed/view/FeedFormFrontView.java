package ru.itzstonlex.desktop.chatbotmessenger.form.feed.view;

import java.util.Collection;
import javafx.geometry.NodeOrientation;
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

  private static final String MICROPHONE_ENABLED_INPUT_MESSAGE_PROMPT = "Говорите сообщение...";
  private static final String MICROPHONE_DISABLED_INPUT_MESSAGE_PROMPT = "Наберите сообщение...";

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

  public void reverseCachedMessagesOrientation(Collection<Node> messageNodes) {
    for (Node node : messageNodes) {
      switch (node.getNodeOrientation()) {

        case RIGHT_TO_LEFT: {
          node.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
          break;
        }
        case LEFT_TO_RIGHT: {
          node.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
          break;
        }
      }

      if (node instanceof Region) {
        reverseCachedMessagesOrientation(((Region) node).getChildrenUnmodifiable());
      }
    }
  }

  public void switchInputMessageFieldPrompt(boolean microphoneEnabled) {
    TextField inputMessageField = find(FeedFormFrontViewConfiguration.INPUT_MESSAGE_FIELD);

    inputMessageField.setPromptText(microphoneEnabled ? MICROPHONE_ENABLED_INPUT_MESSAGE_PROMPT : MICROPHONE_DISABLED_INPUT_MESSAGE_PROMPT);
    inputMessageField.setDisable(microphoneEnabled);
  }

  @Override
  public void configureDisplay(@NonNull FormFrontViewUsecase<FeedFormFrontViewConfiguration> usecase) {
    // nothing.
  }
}
