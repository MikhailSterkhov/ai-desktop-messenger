package ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.type.request.ChatBotRequest;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.observer.ObserveBy;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.FeedForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.function.FeedFormFunctionReleaser;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontViewConfiguration;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.MessageForm;
import ru.itzstonlex.desktop.chatbotmessenger.form.message.function.MessageFormFunctionReleaser.SenderType;
import ru.itzstonlex.desktop.chatbotmessenger.observer.FooterButtonSendClickObserver;
import ru.itzstonlex.desktop.chatbotmessenger.observer.FooterMessageInputEnterObserver;

public final class BothMessagesReceiveController extends AbstractComponentController<FeedForm> {

  @ObserveBy(FooterMessageInputEnterObserver.class)
  private TextField inputMessageField;

  @ObserveBy(FooterButtonSendClickObserver.class)
  private Button messageSendButton;

  @Getter
  private final ChatBotAssistant chatBotAssistant;

  public BothMessagesReceiveController(FeedForm form, ChatBotAssistant chatBotAssistant) {
    super(form);
    this.chatBotAssistant = chatBotAssistant;
  }

  @Override
  protected void configureController() {
    this.inputMessageField = getForm().getView().find(FeedFormFrontViewConfiguration.INPUT_MESSAGE_FIELD);
    this.messageSendButton = getForm().getView().find(FeedFormFrontViewConfiguration.MESSAGE_SEND_BUTTON);
  }

  private String reformatMessage(String text) {
    return text.replace("<br>", "\n");
  }

  public void onMessageReceive(@NonNull String receivedMessage) {
    ChatBotHeaderController chatBotHeaderController = getForm().getController(ChatBotHeaderController.class);

    // send question
    fireFunction(FeedFormFunctionReleaser.SEND, receivedMessage);
    chatBotHeaderController.setTypingStatus(TypingStatus.TYPING);

    // send answer
    ChatBotRequest chatBotRequest = new ChatBotRequest(receivedMessage);
    chatBotAssistant.requestBestSuggestion(chatBotRequest)
        .thenAccept(response -> fireFunction(FeedFormFunctionReleaser.REPLY, response.getMessageText()));
  }

  @SneakyThrows
  private Node createMessageNode(SenderType senderType, String msg) {
    AbstractSceneForm<?> messageForm = getForm().getSceneLoader()
        .loadUncachedForm(ApplicationFormKeys.MESSAGE);

    // todo - replace to function MessageFormFunctionReleaser.UPDATE_MESSAGE_TEXT
    ((MessageForm) messageForm).updateMessageText(senderType, msg);

    return messageForm.getJavafxNode();
  }

  private void addMessageChildren(ObservableList<Node> childrenList, Node messageNode) {
    FeedFormFrontView view = getForm().getView();

    childrenList.add(view.createWrappedMessageNode(messageNode));
    childrenList.add(view.createMessageEmptySeparator(10));
  }

  public void addMessage(SenderType senderType, String text) {
    VBox messagesBox = getForm().getView().find(FeedFormFrontViewConfiguration.MESSAGES_DISPLAY_BOX);
    Node messageNode = createMessageNode(senderType, reformatMessage(text));

    addMessageChildren(messagesBox.getChildren(), messageNode);

    AnchorPane noMessagesPanel = getForm().getView().find(FeedFormFrontViewConfiguration.NO_MESSAGES_PANEL);

    if (noMessagesPanel.isVisible()) {
      noMessagesPanel.setVisible(false);
    }
  }
}
