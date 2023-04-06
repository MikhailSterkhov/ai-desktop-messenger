package ru.itzstonlex.desktop.itzmsg.type.message.controller;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.controller.ControllerConfiguration;

public final class DeleteActionLabelController extends AbstractComponentController {

  public static final String NAME = "name";
  public static final String MESSAGE_TEXT = "text";

  private Label actionLabel;
  private Label messageLabel;

  private String backupMessageText;
  private Font backupMessageFont;

  public DeleteActionLabelController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void initNodes(@NonNull ControllerConfiguration configuration) {
    actionLabel = configuration.getNode(NAME);
    messageLabel = configuration.getNode(MESSAGE_TEXT);
  }

  @Override
  protected void configureController() {
    actionLabel.setCursor(Cursor.HAND);
    actionLabel.setOnMouseClicked(event -> {

      if (isDeleted()) {
        restoreMessageDeleted();
      } else {
        deleteMessage();
      }

      updateLabelDisplay();
    });
  }

  public boolean isDeleted() {
    return backupMessageFont != null;
  }

  private void saveMessageBackup() {
    backupMessageText = messageLabel.getText();
    backupMessageFont = messageLabel.getFont();
  }

  public void restoreMessageDeleted() {
    setDefaultFont();

    messageLabel.setText(backupMessageText);
    messageLabel.setTextFill(Color.BLACK);

    backupMessageText = null;
    backupMessageFont = null;
  }

  public void deleteMessage() {
    if (isDeleted()) {
      return;
    }

    saveMessageBackup();
    setDeletedFont();

    messageLabel.setText("This message has been deleted.");
    messageLabel.setTextFill(Color.DARKGRAY);
  }

  private void setDeletedFont() {
    Font font = messageLabel.getFont();
    Font deletedFont = Font.font(font.getFamily(), FontWeight.NORMAL, FontPosture.ITALIC, font.getSize());

    messageLabel.setFont(deletedFont);
  }

  private void setDefaultFont() {
    if (backupMessageFont != null) {
      messageLabel.setFont(backupMessageFont);
    }
  }

  private void updateLabelDisplay() {
    if (isDeleted()) {
      actionLabel.setText("Restore");
      actionLabel.setTextFill(Color.web("#c26700"));
    }
    else {
      actionLabel.setText("Delete");
      actionLabel.setTextFill(Color.web("#e80000"));
    }
  }

}
