package ru.itzstonlex.desktop.chatbotmessenger.form.message.controller;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.observer.DeleteActionLabelClickObserver;

public final class DeleteActionLabelController extends AbstractComponentController<MessageForm> {

  private String backupMessageText;
  private Font backupMessageFont;

  @ObserveBy(DeleteActionLabelClickObserver.class)
  private Label deleteActionLabel;

  public DeleteActionLabelController(MessageForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    deleteActionLabel = getForm().getView().find(MessageFormFromViewConfiguration.DELETE_ACTION_LABEL);
    deleteActionLabel.setCursor(Cursor.HAND);
  }

  public boolean isDeleted() {
    return backupMessageFont != null;
  }

  private void saveMessageBackup() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    backupMessageText = label.getText();
    backupMessageFont = label.getFont();
  }

  public void restoreMessageDeleted() {
    setDefaultFont();

    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    label.setText(backupMessageText);
    label.setTextFill(Color.BLACK);

    backupMessageText = null;
    backupMessageFont = null;
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

  public void updateLabelDisplay() {
    if (isDeleted()) {
      deleteActionLabel.setText("Restore");
      deleteActionLabel.setTextFill(Color.web("#c26700"));
    }
    else {
      deleteActionLabel.setText("Delete");
      deleteActionLabel.setTextFill(Color.web("#e80000"));
    }
  }

}
