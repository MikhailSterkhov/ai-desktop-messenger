package ru.itzstonlex.desktop.chatbotmessenger.api.google.translation;

import com.google.cloud.translate.v3.TranslationServiceClient;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApi;

/**
 * Сервис Google API по переводам текста
 * из одного мирового языка в другие.
 * <br>
 * <br>
 * Данный сервис использует следующий тип событий: {@link GoogleTranslationEvent}
 * <br>
 * И реализует следующий внутренний Google сервис: {@link TranslationServiceClient}
 */
public interface GoogleTranslation extends GoogleApi<TranslationServiceClient, GoogleTranslationEvent> {

  /**
   * Автоматическое распознание языка, на котором
   * было написано используемое слово в сигнатуре
   *
   * @param text - текст на распознавание языка.
   */
  String detectLanguageCode(@NonNull String text);

  /**
   * Автоматическое распознание языка, на котором
   * было написано используемое слово в сигнатуре
   *
   * @param text - текст на распознавание языка.
   */
  GoogleLanguage detectLanguage(@NonNull String text);

  void translate(@NonNull String text, @NonNull String languageCodeTarget) throws Exception;

  void translate(@NonNull String text, @NonNull GoogleLanguage languageTarget) throws Exception;

  void translate(@NonNull String text, @NonNull String languageCodeSource, @NonNull String languageCodeTarget) throws Exception;

  void translate(@NonNull String text, @NonNull GoogleLanguage languageSource, @NonNull GoogleLanguage languageTarget) throws Exception;
}
