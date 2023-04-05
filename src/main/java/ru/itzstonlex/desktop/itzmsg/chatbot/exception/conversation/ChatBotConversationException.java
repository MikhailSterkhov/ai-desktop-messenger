package ru.itzstonlex.desktop.itzmsg.chatbot.exception.conversation;

import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.chatbot.exception.AbstractChatBotException;

public class ChatBotConversationException extends AbstractChatBotException {

  private static final String MESSAGE_FORMAT = "ChatBot Conversation exception key - '%s'";

  @Getter
  private final ChatBotConversationExceptionKeys.Key conversationExceptionKey;

  public ChatBotConversationException(@NonNull ChatBotConversationExceptionKeys.Key key) {
    super(MESSAGE_FORMAT, key);
    this.conversationExceptionKey = key;
  }
}
