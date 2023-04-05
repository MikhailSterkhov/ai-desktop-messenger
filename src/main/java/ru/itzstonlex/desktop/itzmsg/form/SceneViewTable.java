package ru.itzstonlex.desktop.itzmsg.form;

import java.io.File;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public interface SceneViewTable {

  String VIEW_FORMS_PATH = ("javafx" + File.separator + "fxml" + File.separator);

  Entry FEED = new Entry("FEED", "", "feed.fxml");
  Entry MESSAGE = new Entry("MESSAGE", "", "message.fxml");

  @Getter
  @ToString
  @EqualsAndHashCode
  @RequiredArgsConstructor
  class Entry {

    private final String usageName;

    private final String titleView;

    private final String resourceView;
  }
}
