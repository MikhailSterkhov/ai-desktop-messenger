package ru.itzstonlex.desktop.itzmsg.type.message.function;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.function.AbstractFormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunction;
import ru.itzstonlex.desktop.itzmsg.type.message.MessageForm;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.MessageTextController;
import ru.itzstonlex.desktop.itzmsg.type.message.controller.MessageTimeController;
import ru.itzstonlex.desktop.itzmsg.type.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.itzmsg.type.message.view.MessageFormFrontView;
import ru.itzstonlex.desktop.itzmsg.utility.ImageViewUtils;
import ru.itzstonlex.desktop.itzmsg.utility.ImageViewUtils.AvatarType;

public final class MessageFormFunctionReleaser extends AbstractFormFunctionReleaser<MessageForm> {

  public enum SenderType {

    CHAT_BOT {
      @Override
      public void updateMessageLabelPosition(Label label) {
        label.setTextAlignment(TextAlignment.RIGHT);
      }

      @Override
      public void updateMessageBoxPosition(Pane messageBox) {
        messageBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      }

      @Override
      public void updateAvatar(ImageView imageView) {
        ImageViewUtils.setAvatarImage(imageView, AvatarType.CHAT_BOT);
      }
    },
    USER {
      @Override
      public void updateMessageLabelPosition(Label label) {
        label.setTextAlignment(TextAlignment.LEFT);
      }

      @Override
      public void updateMessageBoxPosition(Pane messageBox) {
        messageBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      }

      @Override
      public void updateAvatar(ImageView imageView) {
        ImageViewUtils.setAvatarImage(imageView, AvatarType.USER);
      }
    };

    public abstract void updateMessageLabelPosition(Label label);

    public abstract void updateMessageBoxPosition(Pane messageBox);

    public abstract void updateAvatar(ImageView imageView);
  }

  private static final int TRANSLATION_X = 25;

  public static final String UPDATE_MESSAGE_TEXT = "UpdateMessageText";

  @FormFunction(key = UPDATE_MESSAGE_TEXT)
  public void updateMessageText(@NonNull SenderType senderType, @NonNull String newText) {
    MessageForm messageForm = getForm();

    updateControllersData(newText);
    updateMessageDataBySender(messageForm, senderType);
  }

  private void updateControllersData(@NonNull String newText) {
    MessageTimeController messageTimeController = getForm().getController(MessageTimeController.class);
    MessageTextController messageTextController = getForm().getController(MessageTextController.class);

    messageTimeController.updateCurrentTime();
    messageTextController.updateMessageText(newText);
  }

  private void updateMessageDataBySender(@NonNull MessageForm form, @NonNull SenderType senderType) {
    MessageFormFrontView view = form.getView();

    HBox horizontalMessageBox = view.find(MessageFormFromViewConfiguration.MESSAGE_FULL_HORIZONTAL_BOX);
    horizontalMessageBox.setTranslateX(TRANSLATION_X);

    senderType.updateAvatar(view.find(MessageFormFromViewConfiguration.MESSAGE_SENDER_AVATAR));
    //senderType.updateMessageLabelPosition(messageLabel);

    senderType.updateMessageBoxPosition(view.find(MessageFormFromViewConfiguration.MESSAGE_VERTICAL_BOX));
    senderType.updateMessageBoxPosition(horizontalMessageBox);
  }
}
