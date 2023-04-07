package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.exclusion;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public final class ChatBotKeywordMap extends ConcurrentHashMap<String, Integer>
    implements Map<String, Integer> {

  private static final int MIN_TEMPERATURE_VALUE = 1;

  @RequiredArgsConstructor
  static class PredicatedKeyword {

    private final String message;
    private final Predicate<String> predicate;
  }

  private final Map<String, String> keywordsDefault = new IdentityHashMap<>();
  private final Map<String, PredicatedKeyword> keywordsPredicated = new IdentityHashMap<>();
  // ...more maps

  public int temperature(@NonNull String suggestion) {
    return getOrDefault(suggestion, MIN_TEMPERATURE_VALUE);
  }

  public void configureMaps() {
    // todo
  }
}
