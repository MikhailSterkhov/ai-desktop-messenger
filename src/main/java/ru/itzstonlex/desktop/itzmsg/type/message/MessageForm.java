package ru.itzstonlex.desktop.itzmsg.type.message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.FormKeys;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.CopyActionLabelController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.DeleteActionLabelController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.MessageTextController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.MessageTimeController;
import ru.itzstonlex.desktop.itzmsg.type.message.function.MessageFormFunctionReleaser;

public final class MessageForm extends AbstractSceneForm {

  @FXML
  private Label copyActionLabel;

  @FXML
  private Label deleteActionLabel;

  @FXML
  private Label timeLabel;

  @FXML
  private Label messageLabel;

  @FXML
  public VBox messageBox;

  @FXML
  public HBox fullMessageBox;

  @FXML
  public ImageView senderAvatar;

  public MessageForm() {
    super(FormKeys.MESSAGE);
  }

  @Override
  public FormFunctionReleaser<?> newFunctionReleaser() {
    return new MessageFormFunctionReleaser();
  }

  @Override
  public void initializeUsecase(FormUsecase usecase) {
    // nothing.
  }

  @Override
  public void initializeControllers() {
    addController(new MessageTimeController(this).with(MessageTimeController.NAME, timeLabel));
    addController(new MessageTextController(this).with(MessageTextController.NAME, messageLabel));

    addController(new CopyActionLabelController(this)
        .with(CopyActionLabelController.NAME, copyActionLabel)
        .with(CopyActionLabelController.MESSAGE_TEXT, messageLabel));

    addController(new DeleteActionLabelController(this)
        .with(DeleteActionLabelController.NAME, deleteActionLabel)
        .with(DeleteActionLabelController.MESSAGE_TEXT, messageLabel));
  }

  @Deprecated
  public void updateMessageText(@NonNull MessageFormFunctionReleaser.SenderType senderType, @NonNull String newText) {
    getController(MessageTimeController.class).updateCurrentTime();
    getController(MessageTextController.class).updateMessageText(newText);

    fullMessageBox.setTranslateX(25);

    senderType.updateAvatar(senderAvatar);
    //senderType.updateMessageLabelPosition(messageLabel);

    senderType.updateMessageBoxPosition(messageBox);
    senderType.updateMessageBoxPosition(fullMessageBox);
  }
}
