package ru.itzstonlex.desktop.itzmsg.chatbot.conversation.usecase;

import java.util.HashMap;
import ru.itzstonlex.desktop.itzmsg.usecase.AbstractUsecaseManager;

public final class ChatBotConversationUsecase
    extends AbstractUsecaseManager<ChatBotConversationKeys> {

  public ChatBotConversationUsecase() {
    super(new HashMap<>());
  }
}
