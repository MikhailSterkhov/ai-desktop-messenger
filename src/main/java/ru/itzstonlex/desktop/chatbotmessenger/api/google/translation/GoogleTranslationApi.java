package ru.itzstonlex.desktop.chatbotmessenger.api.google.translation;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.v3.DetectLanguageRequest;
import com.google.cloud.translate.v3.DetectLanguageResponse;
import com.google.cloud.translate.v3.DetectedLanguage;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.AbstractGoogleApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.RuntimeBlocker;

public final class GoogleTranslationApi extends AbstractGoogleApi<TranslationServiceClient, GoogleTranslationEvent>
    implements GoogleTranslation {

  private static final String REQUEST_PARENT = LocationName.format("intricate-abbey-273218", "global");
  private static final String MIME_TYPE = ("text/plain");

  @Getter
  private TranslationServiceClient api;

  private final RuntimeBlocker pausedBlocker = new RuntimeBlocker();

  @Override
  public void initGoogleService(@NonNull GoogleCredentials credentials) throws Exception {
    this.api = TranslationServiceClient.create(
        TranslationServiceSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build());
  }

  @Override
  public void resume() throws Exception {
    pausedBlocker.unblock();
  }

  @Override
  public void pause() throws Exception {
    pausedBlocker.checkPrecondition();
    pausedBlocker.block();
  }

  @Override
  public void shutdown() throws Exception {
    pause();

    api.close();
    api.shutdownNow();
  }

  private DetectedLanguage fireDetectedLanguage(@NonNull String text) {
    pausedBlocker.checkPrecondition();

    DetectLanguageResponse detectLanguageResponse = api.detectLanguage(
        DetectLanguageRequest.newBuilder()
            .setContent(text)
            .setParent(REQUEST_PARENT)
            .build());

    List<DetectedLanguage> languagesList =
        detectLanguageResponse.getLanguagesList();

    return languagesList.stream().findFirst().orElse(null);
  }

  @Override
  public String detectLanguageCode(@NonNull String text) {
    Optional<DetectedLanguage> languageOptional = Optional.ofNullable(fireDetectedLanguage(text));
    return languageOptional
        .map(DetectedLanguage::getLanguageCode)
        .orElse(null);
  }

  @Override
  public GoogleLanguage detectLanguage(@NonNull String text) {
    Optional<String> languageCodeOptional = Optional.ofNullable(detectLanguageCode(text));
    return languageCodeOptional
        .map(GoogleLanguage::fromCode)
        .orElse(GoogleLanguage.UNKNOWN);
  }

  @SuppressWarnings("ConstantConditions")
  private void fireTextTranslation(String sourceLang, String targetLang, String text) throws Exception {
    pausedBlocker.checkPrecondition();

    boolean isAutoDetected = (sourceLang == null);
    if (isAutoDetected)
      sourceLang = detectLanguageCode(text);

    TranslateTextResponse translateTextResponse = api.translateText(
        TranslateTextRequest.newBuilder()
            .setSourceLanguageCode(sourceLang)
            .setTargetLanguageCode(targetLang)

            .setParent(REQUEST_PARENT)
            .setMimeType(MIME_TYPE)

            .addContents(text)
            .build()
    );

    Translation translation = translateTextResponse.getTranslationsList()
        .stream()
        .findFirst()
        .orElse(null);

    this.fireEvent(new GoogleTranslationEvent(isAutoDetected, sourceLang, targetLang, text,
        translation.getTranslatedText()));
  }

  @Override
  public void translate(@NonNull String text, @NonNull String languageCodeTarget) throws Exception {
    this.fireTextTranslation(null, languageCodeTarget, text);
  }

  @Override
  public void translate(@NonNull String text, @NonNull GoogleLanguage languageTarget) throws Exception {
    this.fireTextTranslation(null, languageTarget.getCode(), text);
  }

  @Override
  public void translate(@NonNull String text, @NonNull String languageCodeSource, @NonNull String languageCodeTarget) throws Exception {
    this.fireTextTranslation(languageCodeSource, languageCodeTarget, text);
  }

  @Override
  public void translate(@NonNull String text, @NonNull GoogleLanguage languageSource, @NonNull GoogleLanguage languageTarget) throws Exception {
    this.fireTextTranslation(languageSource.getCode(), languageTarget.getCode(), text);
  }
}
