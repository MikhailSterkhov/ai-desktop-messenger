package ru.itzstonlex.desktop.itzmsg.form.type.message.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.TimeUnit;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.itzmsg.utility.Schedulers;

public final class CopyActionLabelController extends AbstractComponentController {

  public CopyActionLabelController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void configureController() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.COPY_ACTION_LABEL);

    label.setCursor(Cursor.HAND);
    label.setOnMouseClicked(event -> {

      copyMessageToBuffer();
      changeToCopiedStatus();

      resetMouseClickedEvent();
    });
  }

  public void copyMessageToBuffer() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);

    StringSelection stringSelection =
        new StringSelection(label.getText());

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }

  public void changeToCopiedStatus() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.COPY_ACTION_LABEL);

    label.setUnderline(false);

    label.setText("Copied");
    label.setTextFill(Color.BLACK);

    Schedulers.lateSync(TimeUnit.SECONDS, 2, this::resetDisplayToDefault);
  }

  private void resetDisplayToDefault() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.COPY_ACTION_LABEL);

    label.setUnderline(true);

    label.setText("Copy");
    label.setTextFill(Color.web("#000fff"));

    configureController();
  }

  private void resetMouseClickedEvent() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.COPY_ACTION_LABEL);

    label.setCursor(Cursor.DEFAULT);

    label.setOnMouseClicked(null);
  }
}
