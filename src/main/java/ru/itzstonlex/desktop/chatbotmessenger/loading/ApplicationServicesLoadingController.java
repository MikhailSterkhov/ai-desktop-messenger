package ru.itzstonlex.desktop.chatbotmessenger.loading;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.FormLoader;

@RequiredArgsConstructor
public final class ApplicationServicesLoadingController {

  private final Stage stage;

  @FXML
  private Label descriptionOfLoading;

  @FXML
  void initialize() {
    LoadingFormManipulator loadingFormManipulator = new LoadingFormManipulator(descriptionOfLoading);

    FormLoader formLoader = new FormLoader(stage);

    AsyncServicesLoader asyncServicesLoader = new AsyncServicesLoader(stage, loadingFormManipulator, formLoader);
    asyncServicesLoader.loadApplicationServices(ApplicationFormKeys.FEED);
  }
}
