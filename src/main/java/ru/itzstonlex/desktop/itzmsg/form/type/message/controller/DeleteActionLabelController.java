package ru.itzstonlex.desktop.itzmsg.form.type.message.controller;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.view.MessageFormFromViewConfiguration;

public final class DeleteActionLabelController extends AbstractComponentController {

  private String backupMessageText;
  private Font backupMessageFont;

  public DeleteActionLabelController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void configureController() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.DELETE_ACTION_LABEL);

    label.setCursor(Cursor.HAND);
    label.setOnMouseClicked(event -> {

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
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    this.backupMessageText = label.getText();
    this.backupMessageFont = label.getFont();
  }

  public void restoreMessageDeleted() {
    setDefaultFont();

    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    label.setText(backupMessageText);
    label.setTextFill(Color.BLACK);

    this.backupMessageText = null;
    this.backupMessageFont = null;
  }

  public void deleteMessage() {
    if (isDeleted()) {
      return;
    }

    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    saveMessageBackup();
    setDeletedFont();

    label.setText("This message has been deleted.");
    label.setTextFill(Color.DARKGRAY);
  }

  private void setDeletedFont() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    Font font = label.getFont();
    Font deletedFont = Font.font(font.getFamily(), FontWeight.NORMAL, FontPosture.ITALIC, font.getSize());

    label.setFont(deletedFont);
  }

  private void setDefaultFont() {
    if (backupMessageFont != null) {

      Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);
      label.setFont(backupMessageFont);
    }
  }

  private void updateLabelDisplay() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.DELETE_ACTION_LABEL);

    if (isDeleted()) {
      label.setText("Restore");
      label.setTextFill(Color.web("#c26700"));
    }
    else {
      label.setText("Delete");
      label.setTextFill(Color.web("#e80000"));
    }
  }

}
