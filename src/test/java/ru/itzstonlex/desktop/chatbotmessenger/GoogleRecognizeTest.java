package ru.itzstonlex.desktop.chatbotmessenger;

import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiCloud;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize.GoogleRecognize;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;

public class GoogleRecognizeTest {

  public static void main(String[] args) throws Exception {
    GoogleRecognize recognizeApi = GoogleApiCloud.getRecognizeApi();

    recognizeApi.initGoogleCredentials(ResourceFactory.openClasspath("/credentials.json"));
    recognizeApi.initGoogleService(recognizeApi.getCredentials());

    recognizeApi.addListener((event, throwable) -> {

      if (throwable != null) {
        throwable.printStackTrace();
        return;
      }

      System.out.println(event.isFinally());
      System.out.println(event.getTranscript());
    });

    recognizeApi.recognizeParallel();
    recognizeApi.resume();
  }
}
