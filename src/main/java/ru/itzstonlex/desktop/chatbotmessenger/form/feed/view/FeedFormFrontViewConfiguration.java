package ru.itzstonlex.desktop.chatbotmessenger.form.feed.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.view.FormFrontViewConfiguration;

public interface FeedFormFrontViewConfiguration
    extends FormFrontViewConfiguration {

  NodeKey<TextField> INPUT_MESSAGE_FIELD = new NodeKey<>("messageField");

  NodeKey<AnchorPane> NO_MESSAGES_PANEL = new NodeKey<>("firstMessageAnnotationPanel");

  NodeKey<VBox> MESSAGES_DISPLAY_BOX = new NodeKey<>("messagesBox");

  NodeKey<Label> USERNAME_LABEL = new NodeKey<>("username");

  NodeKey<Label> USER_STATUS_LABEL = new NodeKey<>("userStatus");

  NodeKey<Button> MESSAGE_SEND_BUTTON = new NodeKey<>("sendButton");

  NodeKey<Button> BOT_SETTINGS_BUTTON = new NodeKey<>("settingsButton");

  NodeKey<Button> CHAT_CLEAR_BUTTON = new NodeKey<>("clearButton");
}
