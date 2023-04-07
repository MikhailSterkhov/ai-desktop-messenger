package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.exception;

public abstract class AbstractChatBotException extends Exception {

  public AbstractChatBotException(Throwable cause, String message, Object... replacements) {
    super(String.format(message, replacements), cause);
  }

  public AbstractChatBotException(String message, Object... replacements) {
    this(null, message, replacements);
  }

  public AbstractChatBotException() {
    super();
  }
}
