package ru.itzstonlex.desktop.itzmsg.form;

import java.io.File;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

public interface FormKeys {

  @Getter
  @ToString
  @EqualsAndHashCode
  @RequiredArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  class FormKey {

    String name;
    String resourceFile;
  }

  String VIEW_FORMS_PATH = ("javafx" + File.separator + "fxml" + File.separator);

  FormKey FEED = new FormKey("FEED", "feed.fxml");
  FormKey MESSAGE = new FormKey("MESSAGE", "message.fxml");
}
