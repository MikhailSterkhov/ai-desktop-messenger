package ru.itzstonlex.desktop.chatbotmessenger.api.google.speak;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum GoogleSpeakConfigType {

  MALE(SsmlVoiceGender.MALE,1, 1),
  FEMALE(SsmlVoiceGender.FEMALE, 1, 1),
  NEUTRAL(SsmlVoiceGender.NEUTRAL, 1, 1),
  ;

  SsmlVoiceGender gender;

  double rate;
  double pitch;
}
