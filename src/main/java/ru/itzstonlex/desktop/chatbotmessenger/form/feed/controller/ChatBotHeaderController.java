package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;

public final class ChatBotHeaderController extends AbstractComponentController<FeedForm> {

  public ChatBotHeaderController(FeedForm form) {
    super(form);
  }

  @Override
  protected void configureController() {
    configureDefaultDisplay();
  }

  private void configureDefaultDisplay() {
    Label username = getForm().getView().find(FeedFormFrontViewConfiguration.USERNAME_LABEL);

    username.setText("Chat Bot Assistant");
    username.setTextFill(Color.WHITE);

    username.setFont(Font.font(18));

    setTypingStatus(TypingStatus.AWAIT);
  }

  public void setTypingStatus(TypingStatus typingStatus) {
    Label userstatus = getForm().getView().find(FeedFormFrontViewConfiguration.USER_STATUS_LABEL);
    typingStatus.inject(userstatus);
  }

  public enum TypingStatus {
    TYPING {
      @Override
      public void inject(Label label) {
        label.setText("Печатает...");
        label.setTextFill(Color.WHITE);
      }
    },

    ONLINE {
      @Override
      public void inject(Label label) {
        label.setText("Ожидание следующего ответа");
        label.setTextFill(Color.LIMEGREEN);
      }
    },

    AWAIT {
      @Override
      public void inject(Label label) {
        label.setText("Ожидание собеседника");
        label.setTextFill(Color.YELLOW);
      }
    },
    ;

    protected abstract void inject(Label label);
  }
}
