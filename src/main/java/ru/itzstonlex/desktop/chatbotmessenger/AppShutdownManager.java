package ru.itzstonlex.desktop.chatbotmessenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;

final class AppShutdownManager {

  private final Object lock = new Object();

  private final List<Runnable> additionalHooksList = Collections.synchronizedList(new ArrayList<>());

  public void addAdditionalHook(@NonNull Runnable hook) {
    synchronized (lock) {
      additionalHooksList.add(hook);
    }
  }

  public void fireAdditionalHooks() {
    synchronized (lock) {
      additionalHooksList.forEach(Runnable::run);
    }
  }

  public void registerShutdownHook() {
    AppShutdownHook shutdownHook = new AppShutdownHook(this);

    Thread shutdownHookThread = new Thread(shutdownHook);

    Runtime runtime = Runtime.getRuntime();
    runtime.addShutdownHook(shutdownHookThread);
  }
}
