package ru.itzstonlex.desktop.itzmsg.chatbot.exception.type;

import lombok.Getter;
import ru.itzstonlex.desktop.itzmsg.chatbot.exception.AbstractChatBotException;

public class ChatBotResponseException extends AbstractChatBotException {

  private static final String MESSAGE_FORMAT = "ChatBot Response exception key - '%s'";

  @Getter
  private final ChatBotTypeExceptionKeys.Key typeExceptionKey;

  public ChatBotResponseException(ChatBotTypeExceptionKeys.Key key) {
    super(MESSAGE_FORMAT, key);
    this.typeExceptionKey = key;
  }
}
