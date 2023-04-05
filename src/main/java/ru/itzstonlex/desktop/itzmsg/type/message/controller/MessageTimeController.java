package ru.itzstonlex.desktop.itzmsg.type.message.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.FormComponentsMap;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubActionStorage;

public final class MessageTimeController extends AbstractComponentController {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

  public static final String NAME = "name";

  private Label label;

  @Override
  protected ControllerSubActionStorage<?> getSubActionsStorage() {
    return null;
  }

  @Override
  protected void initNodes(@NonNull FormComponentsMap map) {
    label = map.getNode(NAME);
  }

  @Override
  protected void configureController() {
    updateCurrentTime();
  }

  public void updateCurrentTime() {
    label.setText(DATE_FORMAT.format(System.currentTimeMillis()));
  }
}
