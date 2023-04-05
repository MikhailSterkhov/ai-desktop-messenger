package ru.itzstonlex.desktop.itzmsg.chatbot.exception.conversation;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

public interface ChatBotConversationExceptionKeys {

  Key INTERVIEWER_NAME_NOT_FOUND = new Key("INTERVIEWER_NAME_NOT_FOUND");

  Key INTERVIEWER_PSEUDONYM_NOT_FOUND = new Key("INTERVIEWER_PSEUDONYM_NOT_FOUND");

  Key CONVERSATION_NO_TOPIC = new Key("CONVERSATION_NO_TOPIC");

  @Getter
  @ToString
  @EqualsAndHashCode
  @RequiredArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  class Key { String name; }
}
