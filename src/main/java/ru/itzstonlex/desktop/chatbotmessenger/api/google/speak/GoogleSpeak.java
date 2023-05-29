package ru.itzstonlex.desktop.chatbotmessenger.api.google.speak;

import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApi;

/**
 * Сервис Google API для воспроизведения
 * и конвертирование текста в голос,
 * который проигрывается пользователю
 * на его устройстве.
 * <br>
 * <br>
 * Данный сервис использует следующий тип событий: {@link GoogleSpeakEvent}
 * <br>
 * И реализует следующий внутренний Google сервис: {@link TextToSpeechClient}
 */
public interface GoogleSpeak extends GoogleApi<TextToSpeechClient, GoogleSpeakEvent> {

  void configure(@NonNull GoogleSpeakSettings settings);

  void synthesizeSpeech(@NonNull String message) throws Exception;

  void synthesizeSpeechAsync(@NonNull String message) throws Exception;

  void synthesizeSpeechConfigured(@NonNull GoogleSpeakSettings settings, @NonNull String message) throws Exception;

  void synthesizeSpeechAsyncConfigured(@NonNull GoogleSpeakSettings settings, @NonNull String message) throws Exception;
}
