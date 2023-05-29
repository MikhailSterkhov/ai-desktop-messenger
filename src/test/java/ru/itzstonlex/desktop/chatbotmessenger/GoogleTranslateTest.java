package ru.itzstonlex.desktop.chatbotmessenger;

import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiCloud;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleLanguage;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleTranslation;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;

public class GoogleTranslateTest {

  public static void main(String[] args) throws Exception {
    GoogleTranslation translationApi = GoogleApiCloud.getTranslationApi();

    translationApi.initGoogleCredentials(ResourceFactory.openClasspath("/credentials.json"));
    translationApi.initGoogleService(translationApi.getCredentials());

    translationApi.addListener((event, throwable) -> {

      String responseText = event.getResponseText();
      String sourceLanguage = event.getSourceLanguage();

      System.out.println(responseText);
      System.out.println(sourceLanguage);

      System.out.println(event.isLanguageAutomaticallyDetected());
    });

    translationApi.translate("Hello world!", GoogleLanguage.RUSSIAN);
  }

}
