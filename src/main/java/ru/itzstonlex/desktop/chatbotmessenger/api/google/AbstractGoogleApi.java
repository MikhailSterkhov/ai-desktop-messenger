package ru.itzstonlex.desktop.chatbotmessenger.api.google;

import com.google.api.gax.core.BackgroundResource;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.NonNull;

public abstract class AbstractGoogleApi<T extends BackgroundResource, V extends GoogleApiEvent>
    implements GoogleApi<T, V> {

  private final Set<GoogleApiListener<V>> listeners = new LinkedHashSet<>();

  @Override
  public void addListener(@NonNull GoogleApiListener<V> listener) {
    listeners.add(listener);
  }

  @Override
  public void fireEvent(@NonNull V event) throws Exception {
    for (GoogleApiListener<V> listener : listeners) {
      listener.actionProcess(event, null);
    }
  }

  @Override
  public void fireExceptionallyEvent(@NonNull Throwable throwable) throws Exception {
    for (GoogleApiListener<V> listener : listeners) {
      listener.actionProcess(null, throwable);
    }
  }

  public void clearListeners() {
    listeners.clear();
  }
}
