package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.conversation.usecase;

import java.util.HashMap;
import ru.itzstonlex.desktop.chatbotmessenger.api.usecase.AbstractUsecaseManager;

public final class ChatBotConversationUsecase
    extends AbstractUsecaseManager<ChatBotConversationKeys> {

  public ChatBotConversationUsecase() {
    super(new HashMap<>());
  }
}
