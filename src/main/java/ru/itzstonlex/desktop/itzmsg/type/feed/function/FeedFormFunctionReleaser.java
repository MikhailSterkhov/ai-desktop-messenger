package ru.itzstonlex.desktop.itzmsg.type.feed.function;

import ru.itzstonlex.desktop.itzmsg.form.function.AbstractFormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunction;
import ru.itzstonlex.desktop.itzmsg.type.feed.FeedForm;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.ChatBotHeaderController;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.itzmsg.type.message.function.MessageFormFunctionReleaser.SenderType;

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
  }

  @FormFunction(key = SEND)
  public void send(String message) {
    BothMessagesReceiveController controller = getForm().getController(BothMessagesReceiveController.class);
    controller.addMessage(SenderType.USER, message);
  }
}
