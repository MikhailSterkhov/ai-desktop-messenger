package ru.itzstonlex.desktop.chatbotmessenger.api.sound;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SoundGroup {

  MESSAGE(SoundPaths.GROUP_MESSAGE),
  USER_ACTION(SoundPaths.GROUP_USER_ACTION),
  ;

  String folderPath;

  public void play(@NonNull Sound sound) {

  }
}
