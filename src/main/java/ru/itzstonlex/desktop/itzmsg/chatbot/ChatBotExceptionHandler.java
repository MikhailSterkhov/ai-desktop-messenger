package ru.itzstonlex.desktop.itzmsg.chatbot;

@FunctionalInterface
public interface ChatBotExceptionHandler {

  void onThrow(Throwable throwable);
}
