package ru.itzstonlex.desktop.chatbotmessenger.loading;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class LoadingFormManipulator {

  private final Label label;

  public void addLore(@NonNull String lore) {
    label.setText(label.getText().concat("\n").concat(lore));
  }

  public void shorError(Exception exception) {
    exception.printStackTrace();
    Platform.runLater(() -> {

      label.setText("Internal error: " + exception.getClass().getName() + " - " + exception.getMessage());
      label.setTextFill(Color.RED);
    });
  }
}
