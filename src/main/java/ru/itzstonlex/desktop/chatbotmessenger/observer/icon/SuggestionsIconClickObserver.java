package ru.itzstonlex.desktop.chatbotmessenger.observer.icon;

import javafx.scene.image.ImageView;
import lombok.NonNull;
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
