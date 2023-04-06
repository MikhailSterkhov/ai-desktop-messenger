package ru.itzstonlex.desktop.itzmsg.chatbot.exception.type;

import ru.itzstonlex.desktop.itzmsg.usecase.IUsecaseKeysStorage;

public interface ChatBotTypeExceptionKeys extends IUsecaseKeysStorage {

  Key PARAMETER_NOT_FOUND = new Key("NO_PARAMETER");

  Key EMPTY_SUGGESTIONS_RESPONSE = new Key("EMPTY_SUGGESTIONS_RESPONSE");
}
