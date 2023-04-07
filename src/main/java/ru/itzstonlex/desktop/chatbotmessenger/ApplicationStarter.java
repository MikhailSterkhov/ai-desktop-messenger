package ru.itzstonlex.desktop.chatbotmessenger;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;
import ru.itzstonlex.desktop.chatbotmessenger.loading.ApplicationServicesLoadingController;

public class ApplicationStarter extends Application {

  private static final String APP_TITLE = ("Messenger by @itzstonlex");

  private static final int APP_WIDTH = 1080;
  private static final int APP_HEIGHT = 720;

  @Override
  public void start(Stage stage) {
    try (Resource resource = ResourceFactory.openClasspath(ResourceGroup.JAVAFX.file(ResourceDirection.JAVAFX_MARKDOWNS,
        "loadpage.fxml"))) {

      FXMLLoader fxmlLoader = new FXMLLoader(resource.toURL());
      fxmlLoader.setControllerFactory(cls -> new ApplicationServicesLoadingController(stage));

      setStageDefaultParameters(stage);

      Parent window = fxmlLoader.load();

      stage.setScene(new Scene(window));
      stage.show();

    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public static void setStageDefaultParameters(Stage stage) {
    System.out.printf("[ApplicationStarter] Set default parameters for frame: \"%s\" (%dx%d px)%n",
        APP_TITLE, APP_WIDTH, APP_HEIGHT);

    stage.setTitle(APP_TITLE);

    stage.setWidth(APP_WIDTH);
    stage.setHeight(APP_HEIGHT);
    // ...
  }

}
