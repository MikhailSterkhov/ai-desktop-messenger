package ru.itzstonlex.desktop.chatbotmessenger;

import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.AppStartManager.JavaFxRunningHook;

final class AppRunningManager {

  private static final boolean APP_DISABLED_RUNNING_STATE = Boolean.FALSE;
  private static final boolean APP_ENABLED_RUNNING_STATE = Boolean.TRUE;

  private final AppStartManager startManager = new AppStartManager();
  private final AppShutdownManager shutdownManager = new AppShutdownManager();

  @Getter
  private boolean isRunning;

  private void log(@NonNull String message, Object... replacements) {
    System.out.printf((message) + "%n", replacements);
  }

  private void switchRunningState(boolean runningStateFlag) {
    log("[AppRunningManager] Running state was changed: " + (runningStateFlag ? "Enabled" : "Disabled"));
    isRunning = runningStateFlag;
  }

  public void startApplication(@NonNull Stage stage) {
    switchRunningState(APP_ENABLED_RUNNING_STATE);
    startManager.construct(
        new JavaFxRunningHook(stage));
  }

  public void hookShutdownApplication() {
    switchRunningState(APP_DISABLED_RUNNING_STATE);
    shutdownManager.registerShutdownHook();
  }

  public void addShutdownHook(@NonNull Runnable hook) {
    shutdownManager.addAdditionalHook(hook);
  }
}
