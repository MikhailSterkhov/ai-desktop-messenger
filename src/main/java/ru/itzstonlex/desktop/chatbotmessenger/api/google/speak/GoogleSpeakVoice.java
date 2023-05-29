package ru.itzstonlex.desktop.chatbotmessenger.api.google.speak;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum GoogleSpeakVoice {

  MALE(SsmlVoiceGender.MALE),
  FEMALE(SsmlVoiceGender.FEMALE),
  NEUTRAL(SsmlVoiceGender.NEUTRAL),
  ;

  SsmlVoiceGender gender;
}
