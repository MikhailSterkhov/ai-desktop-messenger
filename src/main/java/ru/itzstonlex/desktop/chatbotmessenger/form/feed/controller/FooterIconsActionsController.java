package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
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

    BothMessagesReceiveController messagesReceiveController = form.getController(BothMessagesReceiveController.class);
    form.getView().reverseCachedMessagesOrientation(messagesReceiveController.getMessageNodesList());

    if (enabled) {
      MessageFormFunctionReleaser.CHAT_BOT_ORIENTATION = NodeOrientation.RIGHT_TO_LEFT;
      MessageFormFunctionReleaser.USER_ORIENTATION = NodeOrientation.LEFT_TO_RIGHT;
    } else {
      MessageFormFunctionReleaser.CHAT_BOT_ORIENTATION = NodeOrientation.LEFT_TO_RIGHT;
      MessageFormFunctionReleaser.USER_ORIENTATION = NodeOrientation.RIGHT_TO_LEFT;
    }
  }

  public void onMicrophoneStateChanged(boolean enabled) {
    getForm().getView().switchInputMessageFieldPrompt(enabled);

    // todo - 08.04.2023 - change state of auto-recognize
  }
}
