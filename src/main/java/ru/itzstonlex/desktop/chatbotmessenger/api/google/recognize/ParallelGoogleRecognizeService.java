package ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ParallelGoogleRecognizeService {

  private static final int STREAMING_LIMIT = 290000; // ~5 minutes

  private int resultEndTimeInMS;
  private int finalRequestEndTime;
  private double bridgingOffset;

  private boolean newStream;

  private List<ByteString> audioInput = new ArrayList<>();
  private List<ByteString> lastAudioInput = new ArrayList<>();

  private final GoogleRecognizeApi googleSpeechApi;

  private final GoogleRecognizeResponseObserver responseObserver;
  private final StreamingRecognitionConfig streamingRecognitionConfig;

  public void executeParallelSpeech(
      @NonNull ClientStream<StreamingRecognizeRequest> clientStream,
      @NonNull StreamController streamController,
      @NonNull MicrophoneLineBuffer microphoneLineBuffer) throws Exception {

    long startTime = System.currentTimeMillis();

    while (true) {
      if (googleSpeechApi.isPaused())
        continue;

      long estimatedTime = System.currentTimeMillis() - startTime;
      StreamingRecognizeRequest request;

      if (estimatedTime >= STREAMING_LIMIT) {

        clientStream.closeSend();
        streamController.cancel(); // remove Observer

        if (resultEndTimeInMS > 0) {
          finalRequestEndTime = 0;
        }

        resultEndTimeInMS = 0;

        lastAudioInput = audioInput;
        audioInput = new ArrayList<>();

        newStream = true;

        clientStream = googleSpeechApi.getApi().streamingRecognizeCallable().splitCall(responseObserver);

        request = StreamingRecognizeRequest.newBuilder()
            .setStreamingConfig(streamingRecognitionConfig)
            .build();

        startTime = System.currentTimeMillis();
      }
      else {
        if ((newStream) && (lastAudioInput.size() > 0)) {

          @SuppressWarnings("IntegerDivisionInFloatingPointContext")
          double chunkTime = STREAMING_LIMIT / lastAudioInput.size();

          if (chunkTime != 0) {
            if (bridgingOffset < 0)
              bridgingOffset = 0;

            if (bridgingOffset > finalRequestEndTime)
              bridgingOffset = finalRequestEndTime;

            int chunksFromMs = (int) Math.floor((finalRequestEndTime - bridgingOffset) / chunkTime);
            bridgingOffset = (int) Math.floor((lastAudioInput.size() - chunksFromMs) * chunkTime);

            for (int i = chunksFromMs; i < lastAudioInput.size(); i++) {
              request = StreamingRecognizeRequest.newBuilder()
                      .setAudioContent(lastAudioInput.get(i))
                      .build();

              clientStream.send(request);
            }
          }

          newStream = false;
        }

        ByteString tempByteString = ByteString.copyFrom(microphoneLineBuffer.getSharedQueue().take());

        request = StreamingRecognizeRequest.newBuilder().setAudioContent(tempByteString).build();
        audioInput.add(tempByteString);
      }

      clientStream.send(request);
    }
  }
}
