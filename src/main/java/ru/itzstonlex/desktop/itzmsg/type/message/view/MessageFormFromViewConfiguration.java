package ru.itzstonlex.desktop.itzmsg.type.message.view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewConfiguration;

public interface MessageFormFromViewConfiguration
    extends FormFrontViewConfiguration {

  NodeKey<Label> COPY_ACTION_LABEL = new NodeKey<>("copyActionLabel");

  NodeKey<Label> DELETE_ACTION_LABEL = new NodeKey<>("deleteActionLabel");

  NodeKey<Label> MESSAGE_TIME_LABEL = new NodeKey<>("timeLabel");

  NodeKey<Label> MESSAGE_TEXT_LABEL = new NodeKey<>("messageLabel");

  NodeKey<VBox> MESSAGE_VERTICAL_BOX = new NodeKey<>("messageBox");

  NodeKey<HBox> MESSAGE_FULL_HORIZONTAL_BOX = new NodeKey<>("fullMessageBox");

  NodeKey<ImageView> MESSAGE_SENDER_AVATAR = new NodeKey<>("senderAvatar");
}
