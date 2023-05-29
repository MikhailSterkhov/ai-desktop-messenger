package ru.itzstonlex.desktop.chatbotmessenger.api.google;

public interface GoogleApiListener<V extends GoogleApiEvent> {

  void actionProcess(V event, Throwable throwable) throws Exception;
}
