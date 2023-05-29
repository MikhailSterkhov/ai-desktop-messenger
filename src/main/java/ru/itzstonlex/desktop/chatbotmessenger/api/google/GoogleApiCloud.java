package ru.itzstonlex.desktop.chatbotmessenger.api.google;

import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize.GoogleRecognize;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize.GoogleRecognizeApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speak.GoogleSpeak;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speak.GoogleSpeakApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleTranslation;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleTranslationApi;

@UtilityClass
public class GoogleApiCloud {

  private final GoogleSpeak GOOGLE_SPEAK_API = new GoogleSpeakApi();
  private final GoogleRecognize GOOGLE_RECOGNIZE_API = new GoogleRecognizeApi();
  private final GoogleTranslation GOOGLE_TRANSLATION_API = new GoogleTranslationApi();

  public GoogleSpeak getSpeakApi() {
    return GOOGLE_SPEAK_API;
  }

  public GoogleRecognize getRecognizeApi() {
    return GOOGLE_RECOGNIZE_API;
  }

  public GoogleTranslation getTranslationApi() {
    return GOOGLE_TRANSLATION_API;
  }
}
