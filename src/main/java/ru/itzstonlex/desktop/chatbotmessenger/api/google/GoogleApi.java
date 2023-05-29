package ru.itzstonlex.desktop.chatbotmessenger.api.google;

import com.google.api.gax.core.BackgroundResource;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;

public interface GoogleApi<T extends BackgroundResource, V extends GoogleApiEvent> {

  T getApi();

  GoogleCredentials getCredentials();

  void addListener(@NonNull GoogleApiListener<V> listener);

  void clearListeners();

  void fireEvent(@NonNull V event) throws Exception;

  void fireExceptionallyEvent(@NonNull Throwable throwable) throws Exception;

  void initGoogleCredentials(@NonNull Resource credentials) throws Exception;

  void initGoogleService(@NonNull GoogleCredentials credentials) throws Exception;

  void enableServiceProcess(@NonNull T api) throws Exception;

  void resumeServiceProcess(@NonNull T api) throws Exception;

  void pauseServiceProcess(@NonNull T api) throws Exception;

  void shutdownServiceProcess(@NonNull T api) throws Exception;
}
