package ru.itzstonlex.desktop.chatbotmessenger.api.google.speech;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.StreamController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.RecognitionConfig;
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

public final class GoogleSpeechApi extends AbstractGoogleApi<SpeechClient, GoogleSpeechEvent>
    implements GoogleSpeech {

  private final ExecutorService executor = Executors.newCachedThreadPool();
  private final AtomicBoolean serviceStateAtomicBoolean = new AtomicBoolean(false);

  private MicrophoneLineBuffer microphoneLineBuffer;
  private ParallelGoogleSpeechService parallelGoogleSpeechService;
  private Future<?> activeBufferTask;

  @Setter(AccessLevel.PACKAGE)
  private StreamController streamController;

  @Getter
  private SpeechClient api;

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
    this.api = SpeechClient.create(SpeechSettings.newBuilder()
        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
        .build()
    );
  }

  private RecognitionConfig configureRecognitionConfig() {
    return RecognitionConfig.newBuilder()
        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)

        .setLanguageCode("ru-RU")

        .addAlternativeLanguageCodes("en-US")
        .addAlternativeLanguageCodes("es-ES")
        .addAlternativeLanguageCodes("es-US")
        .addAlternativeLanguageCodes("de-DE")
        .addAlternativeLanguageCodes("uk-UA")

        .setSampleRateHertz(16000)
        .setEnableAutomaticPunctuation(true)
        .build();
  }

  private StreamingRecognitionConfig configureStreamingRecognitionConfig(RecognitionConfig recognitionConfig) {
    return StreamingRecognitionConfig.newBuilder()
        .setConfig(recognitionConfig)
        .setInterimResults(true)
        .build();
  }

  @Override
  public void enableServiceProcess(@NonNull SpeechClient api) throws Exception {
    if (activeBufferTask != null)
      throw new IllegalAccessException("Service already enabled!");

    if (microphoneLineBuffer == null)
      initAudioDevices();

    GoogleSpeechResponseObserver responseObserver = new GoogleSpeechResponseObserver(this);
    ClientStream<StreamingRecognizeRequest> clientStream = api.streamingRecognizeCallable()
        .splitCall(responseObserver);

    RecognitionConfig recognitionConfig = configureRecognitionConfig();

    StreamingRecognitionConfig streamingRecognitionConfig =
        configureStreamingRecognitionConfig(recognitionConfig);

    StreamingRecognizeRequest request =
        StreamingRecognizeRequest.newBuilder()
            .setStreamingConfig(streamingRecognitionConfig)
            .build();

    clientStream.send(request);
    openAudioStreaming();

    executor.execute(() -> {

      try {
        parallelGoogleSpeechService = new ParallelGoogleSpeechService(this, responseObserver,
            streamingRecognitionConfig);
        parallelGoogleSpeechService.executeParallelSpeech(clientStream, streamController, microphoneLineBuffer);
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
  public void resumeServiceProcess(@NonNull SpeechClient api) throws Exception {
    switchServiceState(true);
  }

  @Override
  public void pauseServiceProcess(@NonNull SpeechClient api) throws Exception {
    switchServiceState(false);
  }

  @Override
  public void shutdownServiceProcess(@NonNull SpeechClient api) throws Exception {
    clearListeners();

    pauseServiceProcess(api);

    activeBufferTask = null;
    parallelGoogleSpeechService = null;

    api.close();
    api.shutdownNow();
  }
}
