package ru.itzstonlex.desktop.chatbotmessenger;

import java.io.IOException;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;
import ru.itzstonlex.desktop.chatbotmessenger.loading.ApplicationServicesLoadingController;

final class AppStartManager {

  public void construct(@NonNull JavaFxRunningHook javaFxRunningHook) {
    javaFxRunningHook.configureFirstApplicationForm();
    javaFxRunningHook.updateStageParameters();

    // todo - construct other application running hooks.
  }

  @RequiredArgsConstructor
  public static class JavaFxRunningHook {

    private static final int APP_WIDTH = 1080;
    private static final int APP_HEIGHT = 720;
    private static final String APP_TITLE = ("Messenger by @itzstonlex");

    private final Stage stage;

    private void withResources(Consumer<Resource> loadingMarkdownResourceHandler) {
      try (Resource resource = ResourceFactory.openClasspath(ResourceGroup.JAVAFX.file(ResourceDirection.JAVAFX_MARKDOWNS, "loadpage.fxml"))) {
        loadingMarkdownResourceHandler.accept(resource);
      }
      catch (IOException exception) {
        exception.printStackTrace();
      }
    }

    public void configureFirstApplicationForm() {
      withResources(resource -> {

        FXMLLoader fxmlLoader = new FXMLLoader(resource.toURL());
        fxmlLoader.setControllerFactory(cls -> new ApplicationServicesLoadingController(stage));

        try {
          Parent window = fxmlLoader.load();

          stage.setScene(new Scene(window));
          stage.show();
        }
        catch (IOException exception) {
          exception.printStackTrace();
        }
      });
    }

    public void updateStageParameters() {
      System.out.printf("[JavaFxRunningHook] Set default parameters for frame: \"%s\" (%dx%dpx)%n",
          APP_TITLE, APP_WIDTH, APP_HEIGHT);

      stage.setTitle(APP_TITLE);

      stage.setWidth(APP_WIDTH);
      stage.setHeight(APP_HEIGHT);
    }
  }
}
