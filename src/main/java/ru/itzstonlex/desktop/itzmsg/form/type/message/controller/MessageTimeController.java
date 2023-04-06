package ru.itzstonlex.desktop.itzmsg.form.type.message.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.view.MessageFormFromViewConfiguration;

public final class MessageTimeController extends AbstractComponentController {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

  public MessageTimeController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void configureController() {
    updateCurrentTime();
  }

  public void updateCurrentTime() {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TIME_LABEL);
    label.setText(DATE_FORMAT.format(System.currentTimeMillis()));
  }
}
