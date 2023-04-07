package ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.exception.conversation;

import ru.itzstonlex.desktop.chatbotmessenger.api.usecase.IUsecaseKeysStorage;

public interface ChatBotConversationExceptionKeys extends IUsecaseKeysStorage {

  Key INTERVIEWER_NAME_NOT_FOUND = new Key("INTERVIEWER_NAME_NOT_FOUND");

  Key INTERVIEWER_PSEUDONYM_NOT_FOUND = new Key("INTERVIEWER_PSEUDONYM_NOT_FOUND");

  Key CONVERSATION_NO_TOPIC = new Key("CONVERSATION_NO_TOPIC");
}
