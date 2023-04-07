package ru.itzstonlex.desktop.chatbotmessenger.form.message.controller;

import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.factory.PlanoCalendars;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import com.github.itzstonlex.planoframework.simplified.SimplifiedScheduledTask;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.observer.CopyActionLabelClickObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;

public final class CopyActionLabelController extends AbstractComponentController<MessageForm> {

  private static final PlanoScheduler scheduler = PlanoCalendars.createSingleThreadCalendar().getScheduler();

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

    // schedule label resetting
    TaskPlan plan = scheduler.configurePlan(TaskParamCacheBuilder.create()
        .set(TaskParams.TASK_TIME_UNIT, TimeUnit.SECONDS)
        .set(TaskParams.TASK_DELAY, 2L)
        .build());

    scheduler.schedule(plan, (Runnable) () -> Platform.runLater(this::resetDisplayToDefault));
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
