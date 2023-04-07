package ru.itzstonlex.desktop.chatbotmessenger.api.sound;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum GroupedSound {

  MESSAGE_CHATBOT_REPLY(SoundGroup.MESSAGE, Sound.MESSAGE_CHATBOT_REPLY),
  ;

  SoundGroup group;
  Sound sound;

  @Override
  public String toString() {
    return SoundPlayer.formatSoundName(group, sound);
  }
}
