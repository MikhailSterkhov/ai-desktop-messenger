package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.GoogleApiCloud;
import ru.itzstonlex.desktop.chatbotmessenger.api.google.recognize.GoogleRecognize;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.function.MessageFormFunctionReleaser;
import ru.itzstonlex.desktop.chatbotmessenger.observer.icon.DialogSidesIconClickObserver;
import ru.itzstonlex.desktop.chatbotmessenger.observer.icon.MicrophoneIconClickObserver;
import ru.itzstonlex.desktop.chatbotmessenger.observer.icon.SuggestionsIconClickObserver;

public final class FooterIconsActionsController extends AbstractComponentController<FeedForm> {

  @ObserveBy(SuggestionsIconClickObserver.class)
  private ImageView suggestions;

  @ObserveBy(MicrophoneIconClickObserver.class)
  private ImageView microphone;

  @ObserveBy(DialogSidesIconClickObserver.class)
  private ImageView dialogSides;

  public FooterIconsActionsController(FeedForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    FeedFormFrontView view = getForm().getView();

    microphone = view.find(FeedFormFrontViewConfiguration.ICON_MICROPHONE);
    suggestions = view.find(FeedFormFrontViewConfiguration.ICON_SUGGESTIONS);
    dialogSides = view.find(FeedFormFrontViewConfiguration.ICON_DIALOG_SIDES);
  }

  public void onSuggestionsStateChanged(boolean enabled) {
    FooterSuggestionsController footerSuggestionsController = getForm().getController(FooterSuggestionsController.class);
    footerSuggestionsController.updateSuggestionsState(enabled);
  }

  public void onDialogSidesStateChanged(boolean enabled) {
    FeedForm form = getForm();
    FeedFormFrontView view = form.getView();

    BothMessagesReceiveController messagesReceiveController = form.getController(BothMessagesReceiveController.class);
    view.reverseCachedMessagesOrientation(messagesReceiveController.getMessageNodesList());

    // reverse next dialog messages orientation.
    NodeOrientation rightToLeft = NodeOrientation.RIGHT_TO_LEFT;
    NodeOrientation leftToRight = NodeOrientation.LEFT_TO_RIGHT;

    if (enabled) {
      MessageFormFunctionReleaser.CHAT_BOT_ORIENTATION = rightToLeft;
      MessageFormFunctionReleaser.USER_ORIENTATION = leftToRight;
    } else {
      MessageFormFunctionReleaser.CHAT_BOT_ORIENTATION = leftToRight;
      MessageFormFunctionReleaser.USER_ORIENTATION = rightToLeft;
    }
  }

  @SneakyThrows
  public void onMicrophoneStateChanged(boolean enabled) {
    FeedForm form = getForm();
    FeedFormFrontView view = form.getView();

    view.switchInputMessageFieldPrompt(enabled);

    GoogleRecognize googleRecognize = GoogleApiCloud.getRecognizeApi();

    if (enabled) {
      googleRecognize.resume();
      googleRecognize.addListener((event, throwable) -> {

        if (throwable != null) {
          throwable.printStackTrace();
          return;
        }

        String transcript = event.getTranscript();

        TextField inputMessageField = view.find(FeedFormFrontViewConfiguration.INPUT_MESSAGE_FIELD);
        Platform.runLater(() -> inputMessageField.setText(transcript));

        if (event.isFinally()) {
          Thread.sleep(1000);

          Platform.runLater(() -> {

            inputMessageField.clear();

            BothMessagesReceiveController controller = form.getController(BothMessagesReceiveController.class);
            controller.onMessageReceive(transcript);
          });
        }
      });
    }
    else {
      googleRecognize.pause();
      googleRecognize.clearListeners();
    }
  }
}
