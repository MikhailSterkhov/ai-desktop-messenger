package ru.itzstonlex.desktop.chatbotmessenger.observer;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.FooterIconsActionsController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;

public class MicrophoneIconClickObserver extends AbstractMouseClickedObserver<FeedForm> {

  private static final double ENABLED_OPACITY = 1.0;
  private static final double DISABLED_OPACITY = 0.5;

  private ImageView iconView;
  private FooterIconsActionsController controller;

  @Override
  public ApplicationFormKeys.Key getApplicationFormKey() {
    return ApplicationFormKeys.FEED;
  }

  @Override
  public void configure() {
    iconView = getForm().getView().find(FeedFormFrontViewConfiguration.ICON_MICROPHONE);
    controller = getForm().getController(FooterIconsActionsController.class);
  }

  @Override
  public void observe(@NonNull MouseEvent event) {
    boolean isEnabled = iconView.getOpacity() == ENABLED_OPACITY;

    iconView.setOpacity(isEnabled ? DISABLED_OPACITY : ENABLED_OPACITY);
    controller.onMicrophoneStateChanged(!isEnabled);
  }
}
