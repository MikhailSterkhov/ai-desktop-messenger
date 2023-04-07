package ru.itzstonlex.desktop.chatbotmessenger.api.utility;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Schedulers {

  private final ScheduledExecutorService scheduleExecutor
      = Executors.newSingleThreadScheduledExecutor();

  public void late(TimeUnit unit, long time, Runnable runnable) {
    scheduleExecutor.schedule(runnable, time, unit);
  }

  public void lateSync(TimeUnit unit, long time, Runnable runnable) {
    late(unit, time, () -> Platform.runLater(runnable));
  }
}
