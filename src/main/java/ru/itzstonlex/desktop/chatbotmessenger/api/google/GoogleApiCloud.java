package ru.itzstonlex.desktop.chatbotmessenger.api.google;

import com.google.cloud.speech.v1.SpeechClient;
import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speech.GoogleSpeechApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speech.GoogleSpeechEvent;

@UtilityClass
public class GoogleApiFactory {

  private final GoogleSpeechApi GOOGLE_SPEECH_API = new GoogleSpeechApi();

  public GoogleApi<SpeechClient, GoogleSpeechEvent> getSpeechApi() {
    return GOOGLE_SPEECH_API;
  }
}
