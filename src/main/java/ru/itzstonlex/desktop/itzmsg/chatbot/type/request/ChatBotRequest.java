package ru.itzstonlex.desktop.itzmsg.chatbot.type.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatBotRequest {

  private final String prompt;
}
