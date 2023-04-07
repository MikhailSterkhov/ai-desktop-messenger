package ru.itzstonlex.desktop.chatbotmessenger.form.feed.function;

import ru.itzstonlex.desktop.chatbotmessenger.api.form.function.AbstractFormFunctionReleaser;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.function.FormFunction;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.PlaySoundUtils;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.ChatBotHeaderController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.function.MessageFormFunctionReleaser.SenderType;

public final class FeedFormFunctionReleaser
    extends AbstractFormFunctionReleaser<FeedForm> {

  public static final String REPLY = "Reply";
  public static final String SEND = "Send";

  @FormFunction(key = REPLY)
  public void reply(String message) {
    BothMessagesReceiveController bothMessagesReceiveController = getForm().getController(BothMessagesReceiveController.class);
    bothMessagesReceiveController.addMessage(SenderType.CHAT_BOT, message);

    ChatBotHeaderController chatBotHeaderController = getForm().getController(ChatBotHeaderController.class);
    chatBotHeaderController.setTypingStatus(TypingStatus.ONLINE);

    PlaySoundUtils.playPickupSound();
  }

  @FormFunction(key = SEND)
  public void send(String message) {
    BothMessagesReceiveController controller = getForm().getController(BothMessagesReceiveController.class);
    controller.addMessage(SenderType.USER, message);
  }
}
