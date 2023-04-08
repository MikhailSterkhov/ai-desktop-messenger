package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.observer.icon.SuggestionsIconClickObserver;

public final class FooterSuggestionsController extends AbstractComponentController<FeedForm> {

  private static final String[] SUGGESTIONS_ARRAY =
      {
          "Привет, чем ты можешь мне помочь?",
          "Как у тебя дела?",
          "Как тебя зовут?",
      };

  private HBox suggestionsDisplayBox;

  public FooterSuggestionsController(FeedForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    FeedFormFrontView view = getForm().getView();
    suggestionsDisplayBox = view.find(FeedFormFrontViewConfiguration.SUGGESTIONS_DISPLAY_BOX);

    for (String suggestionText : SUGGESTIONS_ARRAY) {
      view.createSuggestionBox(suggestionText);
    }
  }

  public void setSuggestionsVisible(boolean flag) {
    updateSuggestionsState(flag);

    ImageView suggestionsIcon = getForm().getView().find(FeedFormFrontViewConfiguration.ICON_SUGGESTIONS);

    SuggestionsIconClickObserver observer = getForm().getNodeObserver(suggestionsIcon);
    observer.observe(null);
  }

  void updateSuggestionsState(boolean state) {
    suggestionsDisplayBox.setVisible(state);
  }
}
