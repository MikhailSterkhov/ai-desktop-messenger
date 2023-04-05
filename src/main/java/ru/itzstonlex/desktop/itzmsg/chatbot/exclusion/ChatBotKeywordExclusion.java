package ru.itzstonlex.desktop.itzmsg.chatbot.exclusion;

import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.response.ChatBotResponseList;

@RequiredArgsConstructor
public final class ChatBotKeywordExclusion {

  private final String prompt;

  public ChatBotResponseList configureSuggestions() {
    // todo
    throw new UnsupportedOperationException();
  }
}
