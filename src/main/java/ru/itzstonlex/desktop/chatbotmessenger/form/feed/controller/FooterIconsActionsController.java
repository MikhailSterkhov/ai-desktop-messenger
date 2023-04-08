package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import javafx.scene.image.ImageView;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.observer.MicrophoneIconClickObserver;

public final class FooterIconsActionsController extends AbstractComponentController<FeedForm> {

  @ObserveBy(MicrophoneIconClickObserver.class)
  private ImageView microphone;

  public FooterIconsActionsController(FeedForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    FeedFormFrontView view = getForm().getView();

    microphone = view.find(FeedFormFrontViewConfiguration.ICON_MICROPHONE);
    // ... other icons here.
  }

  public void onMicrophoneStateChanged(boolean enabled) {
    // todo - 08.04.2023 - change state of auto-recognize
  }
}
