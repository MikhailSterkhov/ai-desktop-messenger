package ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.StreamController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognitionConfig.Builder;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.AbstractGoogleApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleLanguage;

public final class GoogleRecognizeApi extends AbstractGoogleApi<SpeechClient, GoogleRecognizeEvent>
    implements GoogleRecognize {

  private final ExecutorService executor = Executors.newCachedThreadPool();
  private final AtomicBoolean serviceStateAtomicBoolean = new AtomicBoolean(false);

  private MicrophoneLineBuffer microphoneLineBuffer;
  private ParallelGoogleRecognizeService parallelGoogleRecognizeService;
  private Future<?> activeBufferTask;

  @Setter(AccessLevel.PACKAGE)
  private StreamController streamController;

  @Getter
  private SpeechClient api;

  private StreamingRecognitionConfig streamingRecognitionConfig;
  private GoogleRecognizeResponseObserver responseObserver;
  private ClientStream<StreamingRecognizeRequest> clientStream;

  private void initAudioDevices() throws Exception {
    AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
    Info targetInfo = new Info(TargetDataLine.class, audioFormat);

    if (!AudioSystem.isLineSupported(targetInfo))
      throw new IllegalAccessException("Microphone not supported");

    TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
    microphoneLineBuffer = new MicrophoneLineBuffer(audioFormat, targetDataLine);
  }

  public void switchServiceState(boolean flag) {
    serviceStateAtomicBoolean.set(flag);
  }

  @Override
  public boolean isPaused() {
    return !serviceStateAtomicBoolean.get();
  }

  @Override
  public void initGoogleService(@NonNull GoogleCredentials credentials) throws Exception {
    this.api = SpeechClient.create(
        SpeechSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build());
  }

  private RecognitionConfig configureRecognitionConfig() {
    Builder recognitionConfig = RecognitionConfig.newBuilder()
        .setSampleRateHertz(16000)
        .setEnableAutomaticPunctuation(true)

        .setEncoding(AudioEncoding.LINEAR16)
        .setLanguageCode(GoogleLanguage.RUSSIAN.getCode());

    GoogleLanguage[] languages = GoogleLanguage.values();

    for (GoogleLanguage language : languages)
      if (language != GoogleLanguage.RUSSIAN && language != GoogleLanguage.UNKNOWN)
        recognitionConfig.addAlternativeLanguageCodes(language.getCode());

    return recognitionConfig.build();
  }

  private StreamingRecognitionConfig configureStreamingRecognitionConfig(RecognitionConfig recognitionConfig) {
    return StreamingRecognitionConfig.newBuilder()
        .setConfig(recognitionConfig)
        .setInterimResults(true)
        .build();
  }

  @Override
  public void recognize() throws Exception {
    if (activeBufferTask != null)
      throw new IllegalAccessException("Service already enabled!");

    if (microphoneLineBuffer == null)
      initAudioDevices();

    responseObserver = new GoogleRecognizeResponseObserver(this);
    clientStream = api.streamingRecognizeCallable()
        .splitCall(responseObserver);

    RecognitionConfig recognitionConfig = configureRecognitionConfig();

    streamingRecognitionConfig =
        configureStreamingRecognitionConfig(recognitionConfig);

    StreamingRecognizeRequest request =
        StreamingRecognizeRequest.newBuilder()
            .setStreamingConfig(streamingRecognitionConfig)
            .build();

    clientStream.send(request);
    openAudioStreaming();
  }

  @Override
  public void recognizeParallel() throws Exception {
    recognize();
    executor.execute(() -> {

      try {
        parallelGoogleRecognizeService = new ParallelGoogleRecognizeService(this, responseObserver,
            streamingRecognitionConfig);
        parallelGoogleRecognizeService.executeParallelSpeech(clientStream, streamController, microphoneLineBuffer);
      }
      catch (Exception exception) {
        exception.printStackTrace();
      }
    });
  }

  private void openAudioStreaming() throws Exception {
    switchServiceState(true);

    microphoneLineBuffer.getTargetDataLine().open(
        microphoneLineBuffer.getAudioFormat()
    );

    activeBufferTask = executor.submit(microphoneLineBuffer);
  }

  @Override
  public void resume() throws Exception {
    switchServiceState(true);
  }

  @Override
  public void pause() throws Exception {
    switchServiceState(false);
  }

  @Override
  public void shutdown() throws Exception {
    clearListeners();

    pause();

    activeBufferTask = null;
    parallelGoogleRecognizeService = null;

    api.close();
    api.shutdownNow();
  }
}
