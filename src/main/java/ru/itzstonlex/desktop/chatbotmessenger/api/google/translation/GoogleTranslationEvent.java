package ru.itzstonlex.desktop.chatbotmessenger.api.google.translation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiEvent;

@Getter
@RequiredArgsConstructor
public class GoogleTranslationEvent implements GoogleApiEvent {

  private final boolean isLanguageAutomaticallyDetected;

  private final String sourceLanguage;
  private final String targetLanguage;

  private final String sourceText;
  private final String responseText;
}
