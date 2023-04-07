package ru.itzstonlex.desktop.chatbotmessenger.api.sound;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Sound {

  MESSAGE_CHATBOT_REPLY(SoundPaths.SOUND_MESSAGE_CHATBOT_REPLY),
  ;

  String filePath;
}
