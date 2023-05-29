package ru.itzstonlex.desktop.chatbotmessenger.api.google;

import com.google.api.gax.core.BackgroundResource;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;

/**
 * Данный интерфейс реализует базовые необходимости,
 * требующиеся для реализации компонентов, предоставленных
 * от Google API.
 */
public interface GoogleApi<T extends BackgroundResource, V extends GoogleApiEvent> {

  /**
   * Получение созданного сервиса Google API .
   */
  T getApi();

  /**
   * Получение данных для входа, использующихся
   * для создания и инициализации сервиса Google API.
   */
  GoogleCredentials getCredentials();

  /**
   * Добавление слушателя событий, относящихся к
   * сервисам Google API, их запросов, а также
   * получаемых и обрабатываемых ответов.
   *
   * @param listener - слушатель событий.
   */
  void addListener(@NonNull GoogleApiListener<V> listener);

  /**
   * Очистка всех когда-либо кешированных
   * и используемых слушателей событий
   * сервисов Google API.
   */
  void clearListeners();

  /**
   * Вызов события, относящемуся к обработке данного
   * сервиса Google API.
   *
   * @param event - событие обработки сервиса Google API.
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void fireEvent(@NonNull V event) throws Exception;

  /**
   * Вызов события ошибки, вызванной ранее во время инициализации
   * или обработки текущего сервиса Google API.
   *
   * @param throwable - исключение, которое выбрасывает сервис.
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void fireExceptionallyEvent(@NonNull Throwable throwable) throws Exception;

  /**
   * Инициализация данных для авторизации
   * в текущем сервисе Google API для
   * его корректной внутренней инициализации.
   *
   * @param credentials - ресурс, хранящий параметры авторизации.
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void initGoogleCredentials(@NonNull Resource credentials) throws Exception;

  /**
   * Инициализация текущего сервиса Google API.
   *
   * @param credentials - параметры авторизации.
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void initGoogleService(@NonNull GoogleCredentials credentials) throws Exception;

  /**
   * Восстановление работоспособности сервиса
   * во время паузы.
   *
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void resume() throws Exception;

  /**
   * Приостановить работоспособность сервиса,
   * поставив на паузу.
   *
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void pause() throws Exception;

  /**
   * Полностью выключить текущий сервис без
   * возможности возврата.
   *
   * @throws Exception - исключение (обычно выбрасывается когда сбор данных был некорректен).
   */
  void shutdown() throws Exception;
}
