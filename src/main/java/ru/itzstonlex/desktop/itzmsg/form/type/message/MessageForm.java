package ru.itzstonlex.desktop.itzmsg.form.type.message;

import javafx.scene.layout.HBox;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.type.message.controller.CopyActionLabelController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.controller.DeleteActionLabelController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.controller.MessageTextController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.controller.MessageTimeController;
import ru.itzstonlex.desktop.itzmsg.form.type.message.function.MessageFormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.type.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.itzmsg.form.type.message.view.MessageFormFrontView;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;

public final class MessageForm extends AbstractSceneForm<MessageFormFrontView> {

  public MessageForm() {
    super(ApplicationFormKeys.MESSAGE);
  }

  @Override
  public MessageFormFrontView newFrontView() {
    return new MessageFormFrontView(this);
  }

  @Override
  public FormFunctionReleaser<?> newFunctionReleaser() {
    return new MessageFormFunctionReleaser();
  }

  @Override
  public void initializeUsecase(FormUsecase usecase) {
    // nothing.
  }

  @Override
  public void initializeControllers() {
    addController(new MessageTimeController(this));
    addController(new MessageTextController(this));
    addController(new CopyActionLabelController(this));
    addController(new DeleteActionLabelController(this));
  }

  @Deprecated
  public void updateMessageText(@NonNull MessageFormFunctionReleaser.SenderType senderType, @NonNull String newText) {
    getController(MessageTimeController.class).updateCurrentTime();
    getController(MessageTextController.class).updateMessageText(newText);

    HBox horizontalMessageBox = getView().find(MessageFormFromViewConfiguration.MESSAGE_FULL_HORIZONTAL_BOX);
    horizontalMessageBox.setTranslateX(25);

    senderType.updateAvatar(getView().find(MessageFormFromViewConfiguration.MESSAGE_SENDER_AVATAR));
    //senderType.updateMessageLabelPosition(messageLabel);

    senderType.updateMessageBoxPosition(getView().find(MessageFormFromViewConfiguration.MESSAGE_VERTICAL_BOX));
    senderType.updateMessageBoxPosition(horizontalMessageBox);
  }
}
