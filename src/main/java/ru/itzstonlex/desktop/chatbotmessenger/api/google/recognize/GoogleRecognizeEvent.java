package ru.itzstonlex.desktop.chatbotmessenger.api.google.speech;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiEvent;

@Getter
@RequiredArgsConstructor
public class GoogleSpeechEvent implements GoogleApiEvent {

  private final boolean isFinally;

  private final String transcript;
}
