package ru.itzstonlex.desktop.itzmsg.chatbot.conversation.usecase;

import ru.itzstonlex.desktop.itzmsg.usecase.IUsecaseKeysStorage;

public interface ChatBotConversationKeys extends IUsecaseKeysStorage {

  // Interviewer params.
  Key INTERVIEWER_NAME = new Key("INTERVIEWER_NAME");
  Key INTERVIEWER_PSEUDONYM = new Key("INTERVIEWER_PSEUDONYMv");

  // Topics.
  Key ALL_TOPICS = new Key("ALL_TOPICS");
  Key LAST_TOPIC = new Key("LAST_TOPIC");
  Key FIRST_TOPIC = new Key("FIRST_TOPIC");
}
