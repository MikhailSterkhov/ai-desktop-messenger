package ru.itzstonlex.desktop.chatbotmessenger.observer.icon;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.event.AbstractMouseClickedObserver;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.FooterIconsActionsController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;

public class SuggestionsIconClickObserver extends AbstractFeedFooterIconClickObserver {

  @Override
  protected ImageView findIcon(@NonNull FeedFormFrontView view) {
    return view.find(FeedFormFrontViewConfiguration.ICON_SUGGESTIONS);
  }

  @Override
  protected void onStateChanged(@NonNull FooterIconsActionsController controller, boolean flag) {
    controller.onSuggestionsStateChanged(flag);
  }
}
