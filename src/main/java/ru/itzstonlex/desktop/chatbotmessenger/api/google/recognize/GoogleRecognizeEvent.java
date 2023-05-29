package ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiEvent;

@Getter
@RequiredArgsConstructor
public class GoogleRecognizeEvent implements GoogleApiEvent {

  private final boolean isFinally;

  private final String transcript;
}
