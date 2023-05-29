package ru.itzstonlex.desktop.chatbotmessenger.api.google;

/**
 * Данный интерфейс является маркировочным и
 * помечает своих наследников как реализации, заточенные
 * под хранение данных, исходящих из определенного
 * события, относящемуся к реализации Google API.
 *
 * <pre>
 *   {@code googleApi.fireEvent(new GoogleEventImplement(...));}
 * </pre>
 *
 * В дальнейшем это событие будет обрабатываться
 * на стороне листенера.
 *
 * <pre>
 *   {@code googleApi.addListener((event, throwable) -> ...)}
 * </pre>
 */
public interface GoogleApiEvent {
}
