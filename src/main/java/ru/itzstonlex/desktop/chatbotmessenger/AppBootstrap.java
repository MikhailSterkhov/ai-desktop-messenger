package ru.itzstonlex.desktop.chatbotmessenger;

import com.google.api.gax.core.BackgroundResource;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApi;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiCloud;

public final class AppBootstrap extends Application {

  private void addShutdownHooks(@NonNull AppRunningManager appRunningManager) {
    appRunningManager.addShutdownHook(new GoogleApisShutdownHook());
    // todo - add others shutdown hooks...
  }

  @Override
  public void start(Stage stage) {
    AppRunningManager appRunningManager = new AppRunningManager();

    appRunningManager.startApplication(stage);

    addShutdownHooks(appRunningManager);
    appRunningManager.hookShutdownApplication();
  }

  private static class GoogleApisShutdownHook implements Runnable {

    private static final GoogleApi<?, ?>[] GOOGLE_APIS =
        {
            GoogleApiCloud.getSpeakApi(),
            GoogleApiCloud.getRecognizeApi(),
            GoogleApiCloud.getTranslationApi(),
        };

    @Override
    public void run() {
      try {
        for (GoogleApi<?, ?> googleServiceApi : GOOGLE_APIS) {
          BackgroundResource api = googleServiceApi.getApi();

          if (api != null)
            googleServiceApi.shutdown();
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

}
