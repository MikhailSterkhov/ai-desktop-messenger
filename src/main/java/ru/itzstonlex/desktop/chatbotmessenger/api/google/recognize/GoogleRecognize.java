package ru.itzstonlex.desktop.chatbotmessenger.api.google.speech;

import com.google.cloud.speech.v1.SpeechClient;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApi;

public interface GoogleSpeech extends GoogleApi<SpeechClient, GoogleSpeechEvent> {

  boolean isPaused();
}
