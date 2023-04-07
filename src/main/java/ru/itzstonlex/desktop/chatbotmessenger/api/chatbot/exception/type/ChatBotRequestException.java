package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.exception.type;

import lombok.Getter;
import ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.exception.AbstractChatBotException;

public class ChatBotRequestException extends AbstractChatBotException {

  private static final String MESSAGE_FORMAT = "ChatBot Request exception key - '%s'";

  @Getter
  private final ChatBotTypeExceptionKeys.Key typeExceptionKey;

  public ChatBotRequestException(ChatBotTypeExceptionKeys.Key key) {
    super(MESSAGE_FORMAT, key);
    this.typeExceptionKey = key;
  }
}
