package ru.itzstonlex.desktop.itzmsg.chatbot.type.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChatBotRequestBuilder {

  public static ChatBotRequestBuilder newBuilder() {
    return new ChatBotRequestBuilder();
  }

  private String prompt;

  public ChatBotRequestBuilder prompt(@NonNull String prompt) {
    this.prompt = prompt;
    return this;
  }

  @NonNull
  public ChatBotRequest build() {
    return new ChatBotRequest(prompt);
  }
}
