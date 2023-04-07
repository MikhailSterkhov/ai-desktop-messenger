package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.type.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatBotResponse {

  private final int temperature;

  private final String messageText;
}
