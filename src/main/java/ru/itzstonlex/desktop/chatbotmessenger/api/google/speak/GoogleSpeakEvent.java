package ru.itzstonlex.desktop.chatbotmessenger.api.google.speak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiEvent;

@Getter
@RequiredArgsConstructor
public class GoogleSpeakEvent implements GoogleApiEvent {

  private final String message;
}
