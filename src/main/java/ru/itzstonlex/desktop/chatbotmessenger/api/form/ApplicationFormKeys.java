package ru.itzstonlex.desktop.chatbotmessenger.api.form;

import ru.itzstonlex.desktop.chatbotmessenger.api.usecase.IUsecaseKeysStorage;

public interface ApplicationFormKeys extends IUsecaseKeysStorage {

  Key FEED = new Key("feed.fxml");

  Key MESSAGE = new Key("message.fxml");
}
