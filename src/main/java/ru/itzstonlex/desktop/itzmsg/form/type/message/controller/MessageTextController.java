package ru.itzstonlex.desktop.itzmsg.form.type.message.controller;

import javafx.scene.control.Label;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.view.MessageFormFromViewConfiguration;

public final class MessageTextController extends AbstractComponentController {

  public static final String EMPTY_MSG = "";

  public MessageTextController(AbstractSceneForm<?> form) {
    super(form);
  }

  @Override
  protected void configureController() {
    updateMessageText(EMPTY_MSG);
  }

  public void updateMessageText(String text) {
    Label label = getForm().getView().find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);
    label.setText(text);
  }
}
