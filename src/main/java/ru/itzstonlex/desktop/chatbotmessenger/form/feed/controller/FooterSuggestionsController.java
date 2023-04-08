package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;

public class FooterSuggestionsController extends AbstractComponentController<FeedForm> {

  private static final String[] SUGGESTIONS_ARRAY =
      {
          "Привет, чем ты можешь мне помочь?",
          "Как у тебя дела?",
          "Как тебя зовут?",
      };

  public FooterSuggestionsController(FeedForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    FeedFormFrontView view = getForm().getView();

    for (String suggestionText : SUGGESTIONS_ARRAY) {
      view.createSuggestionBox(suggestionText);
    }
  }
}
