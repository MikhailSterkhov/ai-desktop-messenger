package ru.itzstonlex.desktop.chatbotmessenger.api.google.speech;

import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public final class GoogleSpeechResponseObserver implements ResponseObserver<StreamingRecognizeResponse> {

  private final GoogleSpeechApi googleSpeechApi;

  @Override
  public void onStart(StreamController controller) {
    googleSpeechApi.setStreamController(controller);
  }

  @SneakyThrows
  @Override
  public void onResponse(StreamingRecognizeResponse response) {
    StreamingRecognitionResult result = response.getResultsList().get(0);
    SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

    googleSpeechApi.fireEvent(new GoogleSpeechEvent(result.getIsFinal(), alternative.getTranscript()));
  }

  @Override
  public void onComplete() {
    // do nothing.
  }

  @SneakyThrows
  @Override
  public void onError(Throwable throwable) {
    googleSpeechApi.fireExceptionallyEvent(throwable);
  }
}
