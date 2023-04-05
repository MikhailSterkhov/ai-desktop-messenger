package ru.itzstonlex.desktop.itzmsg.type.message;

import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.CopyActionLabelController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.DeleteActionLabelController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.MessageTextController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.MessageTimeController;
import ru.itzstonlex.desktop.itzmsg.utility.ImageViewUtils;
import ru.itzstonlex.desktop.itzmsg.utility.ImageViewUtils.AvatarType;

public final class MessageForm extends AbstractSceneForm {

  private final MessageTimeController messageTimeController = new MessageTimeController();
  private final MessageTextController messageTextController = new MessageTextController();

  @FXML
  private Label copyActionLabel;

  @FXML
  private Label deleteActionLabel;

  @FXML
  private Label timeLabel;

  @FXML
  private Label messageLabel;

  @FXML
  private VBox messageBox;

  @FXML
  private HBox fullMessageBox;

  @FXML
  private ImageView senderAvatar;

  public MessageForm() {
    super(SceneViewTable.MESSAGE);
  }

  @Override
  public void initializeUsecase(FormUsecase usecase) {
    // nothing.
  }

  @Override
  public void initializeControllers() {
    addController(new CopyActionLabelController()
        .with(CopyActionLabelController.NAME, copyActionLabel)
        .with(CopyActionLabelController.MESSAGE_TEXT, messageLabel));

    addController(new DeleteActionLabelController()
        .with(DeleteActionLabelController.NAME, deleteActionLabel)
        .with(DeleteActionLabelController.MESSAGE_TEXT, messageLabel));

    addController(messageTimeController.with(MessageTimeController.NAME, timeLabel));
    addController(messageTextController.with(MessageTextController.NAME, messageLabel));
  }

  public void updateMessageText(@NonNull SenderType senderType, @NonNull String newText) {
    messageTimeController.updateCurrentTime();
    messageTextController.updateMessageText(newText);

    fullMessageBox.setTranslateX(25);

    senderType.updateAvatar(senderAvatar);
    //senderType.updateMessageLabelPosition(messageLabel);

    senderType.updateMessageBoxPosition(messageBox);
    senderType.updateMessageBoxPosition(fullMessageBox);
  }

  public enum SenderType {

    CHAT_BOT {
      @Override
      protected void updateMessageLabelPosition(Label label) {
        label.setTextAlignment(TextAlignment.RIGHT);
      }

      @Override
      protected void updateMessageBoxPosition(Pane messageBox) {
        messageBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      }

      @Override
      protected void updateAvatar(ImageView imageView) {
        ImageViewUtils.setAvatarImage(imageView, AvatarType.CHAT_BOT);
      }
    },
    USER {
      @Override
      protected void updateMessageLabelPosition(Label label) {
        label.setTextAlignment(TextAlignment.LEFT);
      }

      @Override
      protected void updateMessageBoxPosition(Pane messageBox) {
        messageBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      }

      @Override
      protected void updateAvatar(ImageView imageView) {
        ImageViewUtils.setAvatarImage(imageView, AvatarType.USER);
      }
    };

    protected abstract void updateMessageLabelPosition(Label label);

    protected abstract void updateMessageBoxPosition(Pane messageBox);

    protected abstract void updateAvatar(ImageView imageView);
  }
}
