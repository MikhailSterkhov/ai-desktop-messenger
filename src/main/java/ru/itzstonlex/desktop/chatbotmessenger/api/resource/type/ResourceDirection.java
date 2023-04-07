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
public enum ResourceDirection {

  IMAGES_AVATAR("avatar"),
  IMAGES_WALLPAPER("wallpaper"),

  JAVAFX_STYLESHEETS("css"),
  JAVAFX_MARKDOWNS("fxml"),
  ;

  String path;

  public String file(@NonNull String filename) {
    return (path + File.separator + filename);
  }
}