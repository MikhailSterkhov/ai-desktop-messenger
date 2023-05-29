package ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize;

import com.google.cloud.speech.v1.SpeechClient;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApi;

/**
 * Сервис Google API для распознавания
 * и перевода голоса, взятого из микрофона
 * пользователя в текст.
 * <br>
 * <br>
 * Данный сервис использует следующий тип событий: {@link GoogleRecognizeEvent}
 * <br>
 * И реализует следующий внутренний Google сервис: {@link SpeechClient}
 */
public interface GoogleRecognize extends GoogleApi<SpeechClient, GoogleRecognizeEvent> {

  boolean isPaused();

  void recognize() throws Exception;

  void recognizeParallel() throws Exception;
}
