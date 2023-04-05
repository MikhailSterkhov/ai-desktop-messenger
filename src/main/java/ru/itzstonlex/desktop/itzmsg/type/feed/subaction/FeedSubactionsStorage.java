package ru.itzstonlex.desktop.itzmsg.type.feed.subaction;

import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.AbstractControllerSubActionStorage;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubAction;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.itzmsg.type.message.MessageForm.SenderType;

public final class FeedSubactionsStorage
    extends AbstractControllerSubActionStorage<BothMessagesReceiveController> {

  public static final String REPLY = "Reply";
  public static final String SEND = "Send";


  @ControllerSubAction(key = REPLY)
  public void reply(String message) {
    BothMessagesReceiveController controller = getController();
    controller.addMessage(SenderType.CHAT_BOT, message);

    controller.getBotUserController().setTypingStatus(TypingStatus.ONLINE);
  }

  @ControllerSubAction(key = SEND)
  public void send(String message) {
    BothMessagesReceiveController controller = getController();
    controller.addMessage(SenderType.USER, message);
  }
}
