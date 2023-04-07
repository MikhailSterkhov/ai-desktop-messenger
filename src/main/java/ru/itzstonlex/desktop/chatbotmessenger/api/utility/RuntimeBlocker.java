package ru.itzstonlex.desktop.chatbotmessenger.api.utility;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public final class RuntimeBlocker {

  private boolean isBlocked;

  public void block() {
    isBlocked = true;
  }

  public void unblock() {
    isBlocked = false;
  }

  public void checkPrecondition() {
    if (isBlocked) {
      throw new RuntimeException("This function was blocked for use");
    }
  }
}
