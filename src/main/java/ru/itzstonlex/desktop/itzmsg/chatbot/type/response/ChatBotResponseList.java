package ru.itzstonlex.desktop.itzmsg.chatbot.type.response;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public final class ChatBotResponseList extends LinkedList<ChatBotResponse> {

  private final Object lock = new Object();

  public ChatBotResponseList(List<ChatBotResponse> chatBotResponseList) {
    super(Collections.synchronizedList(chatBotResponseList));
  }

  public ChatBotResponse getBestSuggestion() {
    synchronized (lock) {
      OptionalInt maxTemperatureOptional = stream().mapToInt(ChatBotResponse::getTemperature).max();

      if (maxTemperatureOptional.isPresent()) {
        return stream()
            .filter(chatBotResponse -> chatBotResponse.getTemperature() == maxTemperatureOptional.getAsInt())
            .findFirst().orElse(null);
      }

      return null;
    }
  }
}
