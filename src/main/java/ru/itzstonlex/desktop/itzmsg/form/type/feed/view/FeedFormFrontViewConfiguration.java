package ru.itzstonlex.desktop.itzmsg.form.type.feed.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewConfiguration;

public interface FeedFormFrontViewConfiguration
    extends FormFrontViewConfiguration {

  NodeKey<Label> USERNAME_LABEL = new NodeKey<>("username");

  NodeKey<Label> USER_STATUS_LABEL = new NodeKey<>("userStatus");

  NodeKey<TextField> INPUT_MESSAGE_FIELD = new NodeKey<>("messageField");

  NodeKey<Button> MESSAGE_SEND_BUTTON = new NodeKey<>("sendButton");

  NodeKey<Button> BOT_SETTINGS_BUTTON = new NodeKey<>("settingsButton");

  NodeKey<Button> CHAT_CLEAR_BUTTON = new NodeKey<>("clearButton");

  NodeKey<VBox> LIST_MESSAGES_BOX = new NodeKey<>("messagesBox");

  NodeKey<AnchorPane> NO_MESSAGES_PANEL = new NodeKey<>("firstMessageAnnotationPanel");
}
