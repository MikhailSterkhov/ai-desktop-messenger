package ru.itzstonlex.desktop.chatbotmessenger.form.message.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.TimeUnit;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.observer.CopyActionLabelClickObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.Schedulers;

public final class CopyActionLabelController extends AbstractComponentController<MessageForm> {

  @ObserveBy(CopyActionLabelClickObserver.class)
  private Label copyActionLabel;

  public CopyActionLabelController(MessageForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    copyActionLabel = getForm().getView().find(MessageFormFromViewConfiguration.COPY_ACTION_LABEL);
    copyActionLabel.setCursor(Cursor.HAND);
  }

  public void copyMessageToBuffer() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    StringSelection stringSelection =
        new StringSelection(label.getText());

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }

  public void changeToCopiedStatus() {
    copyActionLabel.setUnderline(false);

    copyActionLabel.setText("Copied");
    copyActionLabel.setTextFill(Color.BLACK);

    Schedulers.lateSync(TimeUnit.SECONDS, 2, this::resetDisplayToDefault);
  }

  private void resetDisplayToDefault() {
    copyActionLabel.setCursor(Cursor.HAND);

    copyActionLabel.setUnderline(true);

    copyActionLabel.setText("Copy");
    copyActionLabel.setTextFill(Color.web("#000fff"));
  }

  public void resetMouseClickedEvent() {
    copyActionLabel.setCursor(Cursor.DEFAULT);
  }
}
