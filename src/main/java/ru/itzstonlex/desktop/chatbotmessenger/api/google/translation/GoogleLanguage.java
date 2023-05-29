package ru.itzstonlex.desktop.chatbotmessenger.api.google.translation;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum GoogleLanguage {

  UKRAINIAN("ua-UK"),
  RUSSIAN("ru-RU"),
  ENGLISH("en-US"),
  GERMAN("de-DE"),
  FRANCE("fr-FR"),

  UNKNOWN(null)
  ;

  String code;

  public static GoogleLanguage fromCode(@NonNull String code) {
    for (GoogleLanguage language : values()) {

      if (Objects.equals(language.code, code)) {
        return language;
      }
    }

    return UNKNOWN;
  }
}
