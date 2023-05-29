package ru.itzstonlex.desktop.chatbotmessenger.observer.icon;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.FooterIconsActionsController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;

public abstract class AbstractFeedFooterIconClickObserver extends AbstractMouseClickedObserver<FeedForm> {

  private static final double ENABLED_OPACITY = 1.0;
  private static final double DISABLED_OPACITY = 0.5;

  private ImageView iconView;
  private FooterIconsActionsController controller;

  protected abstract ImageView findIcon(@NonNull FeedFormFrontView view);

  protected abstract void onStateChanged(@NonNull FooterIconsActionsController controller, boolean flag);

  @Override
  public ApplicationFormKeys.Key getApplicationFormKey() {
    return ApplicationFormKeys.FEED;
  }

  @Override
  public void configure() {
    FeedForm form = getForm();

    controller = form.getController(FooterIconsActionsController.class);
    iconView = findIcon(form.getView());
  }

  @Override
  public void observe(MouseEvent event) {
    boolean isEnabled = iconView.getOpacity() == ENABLED_OPACITY;

    log(MESSAGE_OBSERVE_PROCESS + ", state: " + (isEnabled ? "'enabled' -> 'disabled'" : "'disabled' -> 'enabled'"));

    iconView.setOpacity(isEnabled ? DISABLED_OPACITY : ENABLED_OPACITY);
    onStateChanged(controller, !isEnabled);
  }
}
