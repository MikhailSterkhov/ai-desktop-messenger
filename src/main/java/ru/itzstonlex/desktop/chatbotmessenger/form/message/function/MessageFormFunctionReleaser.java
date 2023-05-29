package ru.itzstonlex.desktop.chatbotmessenger.form.message.function;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.function.AbstractFormFunctionReleaser;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.function.FormFunction;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.ImageViewUtils;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.ImageViewUtils.AvatarType;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.controller.MessageTextController;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.controller.MessageTimeController;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFromViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.view.MessageFormFrontView;

public final class MessageFormFunctionReleaser extends AbstractFormFunctionReleaser<MessageForm> {

  private static final int TRANSLATION_X = 25;

  public static final String UPDATE_MESSAGE_TEXT = "UpdateMessageText";

  public static NodeOrientation CHAT_BOT_ORIENTATION = NodeOrientation.LEFT_TO_RIGHT;
  public static NodeOrientation USER_ORIENTATION = NodeOrientation.RIGHT_TO_LEFT;

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

    // configure based form components.
    Label messageLabel = view.find(MessageFormFromViewConfiguration.MESSAGE_TEXT_LABEL);
    messageLabel.setAlignment(Pos.TOP_LEFT);

    HBox horizontalMessageBox = view.find(MessageFormFromViewConfiguration.MESSAGE_FULL_HORIZONTAL_BOX);
    horizontalMessageBox.setTranslateX(TRANSLATION_X);

    // update component params by sender.
    senderType.updateAvatarImageView(view.find(MessageFormFromViewConfiguration.MESSAGE_SENDER_AVATAR));

    senderType.updateMessageBoxPosition(view.find(MessageFormFromViewConfiguration.MESSAGE_VERTICAL_BOX));
    senderType.updateMessageBoxPosition(horizontalMessageBox);
  }

  public enum SenderType {

    CHAT_BOT {
      @Override
      public void updateMessageBoxPosition(Pane messageBox) {
        messageBox.setNodeOrientation(CHAT_BOT_ORIENTATION);
      }

      @Override
      public void updateAvatarImageView(ImageView imageView) {
        ImageViewUtils.setAvatarImage(imageView, AvatarType.CHAT_BOT);
      }
    },
    USER {
      @Override
      public void updateMessageBoxPosition(Pane messageBox) {
        messageBox.setNodeOrientation(USER_ORIENTATION);
      }

      @Override
      public void updateAvatarImageView(ImageView imageView) {
        ImageViewUtils.setAvatarImage(imageView, AvatarType.USER);
      }
    };

    public abstract void updateMessageBoxPosition(Pane messageBox);

    public abstract void updateAvatarImageView(ImageView imageView);
  }
}
