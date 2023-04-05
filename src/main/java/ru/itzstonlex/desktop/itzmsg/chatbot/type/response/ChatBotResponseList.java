package ru.itzstonlex.desktop.itzmsg.chatbot.type.response;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class ChatBotResponseList extends LinkedList<ChatBotResponse> {

  private final Object lock = new Object();

  public ChatBotResponseList(List<ChatBotResponse> chatBotResponseList) {
    super(Collections.synchronizedList(chatBotResponseList));
  }

  public ChatBotResponse getBestSuggestion() {
    synchronized (lock) {
      // todo
      throw new UnsupportedOperationException();
    }
  }
}
