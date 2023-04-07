package ru.itzstonlex.desktop.chatbotmessenger.loading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.FormLoader;

@RequiredArgsConstructor
public final class AsyncServicesLoader {

  public static final ApplicationFormKeys.Key[] FORMS_TO_LOAD =
      {
          ApplicationFormKeys.FEED,
          ApplicationFormKeys.MESSAGE,
      };

  private static final ApplicationServices[] APPLICATION_SERVICES_CONSTS = ApplicationServices.values();
  private static final ExecutorService ASYNCHRONOUS_SERVICE = Executors.newCachedThreadPool();

  private static final String DEBUG_PREFIX = "[Loading] ";

  @Getter
  private final Stage stage;

  @Getter
  private final LoadingFormManipulator formManipulator;

  @Getter
  private final FormLoader formLoader;

  void debug(String message, Object... replacements) {
    System.out.printf(DEBUG_PREFIX + message.concat("%n"), replacements);
  }

  void async(Runnable runnable) {
    ASYNCHRONOUS_SERVICE.execute(runnable);
  }

  void sync(Runnable runnable) {
    Platform.runLater(runnable);
  }

  @SneakyThrows
  void await(double seconds) {
    Thread.sleep((long) (seconds * 1000L));
  }

  public void loadApplicationServices() {
    debug("Run asynchronous application loading...");
    async(() -> {

      for (ApplicationServices applicationService : APPLICATION_SERVICES_CONSTS) {
        await(1.25);
        applicationService.load(this);
      }

      await(3);
    });
  }
}
