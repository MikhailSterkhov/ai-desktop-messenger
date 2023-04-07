package ru.itzstonlex.desktop.chatbotmessenger.api.resource.type;

import java.io.File;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ResourceGroup {

  IMAGES("img"),
  JAVAFX("javafx"),
  SOUNDS("sound"),
  ;

  String path;

  public String file(@NonNull ResourceDirection direction, @NonNull String filename) {
    return (path + File.separator + direction.file(filename));
  }
}