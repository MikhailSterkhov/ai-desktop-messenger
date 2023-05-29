package ru.itzstonlex.desktop.chatbotmessenger;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AppShutdownHook implements Runnable {

  private final AppShutdownManager shutdownManager;

  @Override
  public void run() {
    shutdownManager.fireAdditionalHooks();
    System.out.println("[AppShutdownHook] Thanks for using and goodbye!");
  }
}
