package ru.itzstonlex.desktop.itzmsg.chatbot.exception;

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
