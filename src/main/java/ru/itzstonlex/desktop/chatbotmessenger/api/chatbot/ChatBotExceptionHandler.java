package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot;

@FunctionalInterface
public interface ChatBotExceptionHandler {

  void onThrow(Throwable throwable);
}
