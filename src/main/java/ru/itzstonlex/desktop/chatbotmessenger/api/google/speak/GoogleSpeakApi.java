package ru.itzstonlex.desktop.chatbotmessenger.api.google.speak;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechRequest;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.TextToSpeechSettings;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.AbstractGoogleApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.sound.SoundPlayer;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.RuntimeBlocker;

public final class GoogleSpeakApi extends AbstractGoogleApi<TextToSpeechClient, GoogleSpeakEvent>
    implements GoogleSpeak {

  private final RuntimeBlocker pausedBlocker = new RuntimeBlocker();
  private final ExecutorService executor = Executors.newCachedThreadPool();

  private GoogleSpeakSettings settings = GoogleSpeakSettings.DEFAULT;

  @Getter
  private TextToSpeechClient api;

  @Override
  public void initGoogleService(@NonNull GoogleCredentials credentials) throws Exception {
    this.api = TextToSpeechClient.create(
        TextToSpeechSettings.newBuilder()
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

    executor.shutdownNow();
  }

  @Override
  public void configure(@NonNull GoogleSpeakSettings settings) {
    this.settings = settings;
  }

  private void fireSynthesizeSpeechRequest(@NonNull String message) throws Exception {
    String languageCode = (settings.getLanguageCode() == null
        ? settings.getLanguage().getCode()
        : settings.getLanguageCode());

    SynthesizeSpeechRequest synthesizeSpeechRequest =
        SynthesizeSpeechRequest.newBuilder()
            .setInput(SynthesisInput.newBuilder()
                .setText(message)
                .build())
            .setAudioConfig(AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .setSampleRateHertz(16000)
                .setSpeakingRate(settings.parseSpeedDouble())
                .setPitch(settings.parsePitchDouble())
                .build())
            .setVoice(VoiceSelectionParams.newBuilder()
                .setLanguageCode(languageCode)
                .setSsmlGender(settings.getVoiceGender().getGender())
                .build())
            .build();

    SynthesizeSpeechResponse synthesizeSpeechResponse = api.synthesizeSpeech(
        synthesizeSpeechRequest);

    SoundPlayer.playInputStreamSynchronized(synthesizeSpeechResponse.getAudioContent().newInput());
    fireEvent(new GoogleSpeakEvent(message));
  }

  @Override
  public void synthesizeSpeech(@NonNull String message) throws Exception {
    fireSynthesizeSpeechRequest(message);
  }

  @Override
  public void synthesizeSpeechAsync(@NonNull String message) {
    executor.execute(() -> {
      try {
        synthesizeSpeech(message);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public void synthesizeSpeechConfigured(@NonNull GoogleSpeakSettings settings, @NonNull String message) throws Exception {
    configure(settings);
    synthesizeSpeech(message);
  }

  @Override
  public void synthesizeSpeechAsyncConfigured(@NonNull GoogleSpeakSettings settings, @NonNull String message) {
    configure(settings);
    synthesizeSpeechAsync(message);
  }
}
