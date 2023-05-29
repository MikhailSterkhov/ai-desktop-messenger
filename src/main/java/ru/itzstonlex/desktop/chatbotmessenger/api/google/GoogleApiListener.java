package ru.itzstonlex.desktop.chatbotmessenger.api.google;

/**
 * Данный интерфейс является функциональным и
 * помечает своих наследников как реализации, заточенные
 * под использование и обработку собранных данных
 * с событий, относящихся к реализации Google API.
 *
 * <pre>
 *   {@code googleApi.addListener((event, throwable) -> ...)}
 * </pre>
 */
@FunctionalInterface
public interface GoogleApiListener<V extends GoogleApiEvent> {

  void actionProcess(V event, Throwable throwable) throws Exception;
}
