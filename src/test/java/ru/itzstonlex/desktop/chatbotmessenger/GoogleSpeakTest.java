package ru.itzstonlex.desktop.chatbotmessenger;

import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiCloud;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speak.GoogleSpeak;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speak.GoogleSpeakSettings;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.speak.GoogleSpeakVoice;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.translation.GoogleLanguage;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;

public class GoogleSpeakTest {

  public static void main(String[] args) throws Exception {
    GoogleSpeak speakApi = GoogleApiCloud.getSpeakApi();

    speakApi.initGoogleCredentials(ResourceFactory.openClasspath("/credentials.json"));
    speakApi.initGoogleService(speakApi.getCredentials());

    speakApi.addListener((event, throwable) -> {

      System.out.println(event.getMessage());
    });

    speakApi.configure(
        GoogleSpeakSettings.newBuilder()
            .setVoiceGender(GoogleSpeakVoice.FEMALE)
            .setLanguage(GoogleLanguage.ENGLISH)

            .setSpeed(100)
            .setPitch(100)
            .build());

    for (int i = 0; i < 20; i++) {
      Thread.sleep(100);
      speakApi.synthesizeSpeechAsync("what a fuck is govnocode sergay lets go to my dick fucking slave");
    }
  }
}
