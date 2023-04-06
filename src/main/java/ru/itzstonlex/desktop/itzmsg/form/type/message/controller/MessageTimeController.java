package ru.itzstonlex.desktop.itzmsg.form.type.message.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.ControllerConfiguration;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;

public final class MessageTimeController extends AbstractComponentController {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

  public static final String NAME = "name";

  private Label label;

  public MessageTimeController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void initNodes(@NonNull ControllerConfiguration configuration) {
    label = configuration.getNode(NAME);
  }

  @Override
  protected void configureController() {
    updateCurrentTime();
  }

  public void updateCurrentTime() {
    label.setText(DATE_FORMAT.format(System.currentTimeMillis()));
  }
}
