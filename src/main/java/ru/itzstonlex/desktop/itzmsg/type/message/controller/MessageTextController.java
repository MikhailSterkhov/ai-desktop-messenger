package ru.itzstonlex.desktop.itzmsg.type.message.controller;

import javafx.scene.control.Label;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.FormComponentsMap;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubActionStorage;

public final class MessageTextController extends AbstractComponentController {

  public static final String EMPTY_MSG = "";
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
    updateMessageText(EMPTY_MSG);
  }

  public void updateMessageText(String text) {
    label.setText(text);
  }
}
