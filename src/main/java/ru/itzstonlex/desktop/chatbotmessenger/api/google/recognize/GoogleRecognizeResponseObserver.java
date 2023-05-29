package ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize;

import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public final class GoogleRecognizeResponseObserver implements ResponseObserver<StreamingRecognizeResponse> {

  private final GoogleRecognizeApi googleRecognizeApi;

  @Override
  public void onStart(StreamController controller) {
    googleRecognizeApi.setStreamController(controller);
  }

  @SneakyThrows
  @Override
  public void onResponse(StreamingRecognizeResponse response) {
    List<StreamingRecognitionResult> resultsList = response.getResultsList();

    for (StreamingRecognitionResult result : resultsList) {
      List<SpeechRecognitionAlternative> alternativesList = result.getAlternativesList();

      for (SpeechRecognitionAlternative alternative : alternativesList) {
        googleRecognizeApi.fireEvent(new GoogleRecognizeEvent(result.getIsFinal(), alternative.getTranscript()));
      }
    }
  }

  @Override
  public void onComplete() {
    // do nothing.
  }

  @SneakyThrows
  @Override
  public void onError(Throwable throwable) {
    googleRecognizeApi.fireExceptionallyEvent(throwable);
  }
}
