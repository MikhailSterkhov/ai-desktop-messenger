package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;

/**
 * Override based {@link NodeObserver}
 * interface methods.
 */
public abstract class AbstractNodeObserver<T extends AbstractSceneForm<?>>
    implements NodeObserver<T> {

  private static final String PREFIX = "[%s] ";

  protected static final String MESSAGE_OBSERVE_BEGIN = "Observer was registered";
  protected static final String MESSAGE_OBSERVE_PROCESS = "Process node observe";

  @Getter
  @Setter
  private T form;

  @Getter
  @Setter
  private Node component;

  public final void log(String message, Object... replacements) {
    System.out.println(String.format(PREFIX, getClass().getSimpleName()) + String.format(message, replacements));
  }
}
