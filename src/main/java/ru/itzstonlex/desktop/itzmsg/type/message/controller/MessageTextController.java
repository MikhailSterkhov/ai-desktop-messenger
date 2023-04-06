package ru.itzstonlex.desktop.itzmsg.type.message.controller;

import javafx.scene.control.Label;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.ControllerConfiguration;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;

public final class MessageTextController extends AbstractComponentController {

  public static final String EMPTY_MSG = "";
  public static final String NAME = "name";

  private Label label;

  public MessageTextController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void initNodes(@NonNull ControllerConfiguration configuration) {
    label = configuration.getNode(NAME);
  }

  @Override
  protected void configureController() {
    updateMessageText(EMPTY_MSG);
  }

  public void updateMessageText(String text) {
    label.setText(text);
  }
}
