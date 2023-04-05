package ru.itzstonlex.desktop.itzmsg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable;
import ru.itzstonlex.desktop.itzmsg.loading.LoadingJavaFXController;

public class ApplicationStarter extends Application {

  private static final String LOADPAGE_PATH = (File.separator + SceneViewTable.VIEW_FORMS_PATH + "loadpage.fxml");

  private static final String APP_TITLE = "Messenger by @itzstonlex";

  private static final int APP_WIDTH = 1080;
  private static final int APP_HEIGHT = 720;

  @Override
  public void start(Stage stage) {
    URL resource = getClass().getResource(LOADPAGE_PATH);

    FXMLLoader fxmlLoader = new FXMLLoader(resource);
    fxmlLoader.setControllerFactory(cls -> new LoadingJavaFXController(stage));

    try {
      setStageDefaultParameters(stage);

      Parent window = fxmlLoader.load();

      stage.setScene(new Scene(window));
      stage.show();

    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public static void setStageDefaultParameters(Stage stage) {
    stage.setTitle(APP_TITLE);

    stage.setWidth(APP_WIDTH);
    stage.setHeight(APP_HEIGHT);
    // ...

    System.out.println("[ApplicationStarter] Set default parameters for frame: \""
        + APP_TITLE + "\" (" + APP_WIDTH + "x" + APP_HEIGHT + " px)");
  }

}
