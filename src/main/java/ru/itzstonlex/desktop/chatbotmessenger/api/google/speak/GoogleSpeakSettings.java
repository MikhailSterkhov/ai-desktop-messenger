package ru.itzstonlex.desktop.chatbotmessenger.api.google.speak;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleLanguage;

@Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true, setterPrefix = "set")
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class GoogleSpeakSettings {

  public static final int SPEED_MAX_VALUE = 100;
  public static final int PITCH_MAX_VALUE = 100;

  public static final GoogleSpeakSettings DEFAULT = new GoogleSpeakSettings("ru-RU", GoogleLanguage.RUSSIAN,
      GoogleSpeakVoice.MALE, SPEED_MAX_VALUE, PITCH_MAX_VALUE);

  String languageCode;

  GoogleLanguage language;

  GoogleSpeakVoice voiceGender;

  int speed;
  int pitch;

  public double parseSpeedDouble() {
    return (double) speed / 100D;
  }

  public double parsePitchDouble() {
    return (double) speed / 100D;
  }
}
