package ru.itzstonlex.desktop.chatbotmessenger.api.google;

import com.google.api.gax.core.BackgroundResource;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;

public abstract class AbstractGoogleApi<T extends BackgroundResource, V extends GoogleApiEvent>
    implements GoogleApi<T, V> {

  private static final String SCOPED_CLOUD_PLATFORM = "https://www.googleapis.com/auth/cloud-platform";

  private final Set<GoogleApiListener<V>> listeners = new LinkedHashSet<>();

  @Getter
  private GoogleCredentials credentials;

  @Override
  public void initGoogleCredentials(@NonNull Resource credentials) throws Exception {
    this.credentials = ServiceAccountCredentials.fromStream(credentials.toLocalInputStream())
        .createScoped(Collections.singletonList(SCOPED_CLOUD_PLATFORM));
  }

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

  @Override
  public void clearListeners() {
    listeners.clear();
  }
}
