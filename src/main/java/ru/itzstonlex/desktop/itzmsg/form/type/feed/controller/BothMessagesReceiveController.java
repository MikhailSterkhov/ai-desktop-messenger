package ru.itzstonlex.desktop.itzmsg.form.type.feed.controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.request.ChatBotRequest;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.observer.ObserveBy;
import ru.itzstonlex.desktop.itzmsg.form.observer.impl.FooterButtonSendClickObserver;
import ru.itzstonlex.desktop.itzmsg.form.observer.impl.FooterMessageInputEnterObserver;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.function.FeedFormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.view.FeedFormFrontViewConfiguration;
import ru.itzstonlex.desktop.itzmsg.form.type.message.MessageForm;
import ru.itzstonlex.desktop.itzmsg.form.type.message.function.MessageFormFunctionReleaser.SenderType;

public final class BothMessagesReceiveController extends AbstractComponentController {

  @ObserveBy(FooterMessageInputEnterObserver.class)
  private TextField inputMessageField;

  @ObserveBy(FooterButtonSendClickObserver.class)
  private Button messageSendButton;

  @Getter
  private final ChatBotAssistant chatBotAssistant;

  public BothMessagesReceiveController(AbstractSceneForm<?> form, ChatBotAssistant chatBotAssistant) {
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

  @SuppressWarnings("unchecked")
  private void addMessageChildren(ObservableList<Node> childrenList, Node messageNode) {
    AbstractSceneForm<FeedFormFrontView> form = (AbstractSceneForm<FeedFormFrontView>) getForm();

    FeedFormFrontView view = form.getView();

    childrenList.add(view.createWrappedMessageNode(messageNode));
    childrenList.add(view.createMessageEmptySeparator(10));
  }

  @SuppressWarnings("unchecked")
  public void addMessage(SenderType senderType, String text) {
    AbstractSceneForm<FeedFormFrontView> form = (AbstractSceneForm<FeedFormFrontView>) getForm();
    VBox messagesBox = form.getView().find(FeedFormFrontViewConfiguration.MESSAGES_DISPLAY_BOX);

    Node messageNode = createMessageNode(senderType, reformatMessage(text));

    addMessageChildren(messagesBox.getChildren(), messageNode);

    AnchorPane noMessagesPanel = form.getView().find(FeedFormFrontViewConfiguration.NO_MESSAGES_PANEL);
    if (noMessagesPanel.isVisible()) {
      noMessagesPanel.setVisible(false);
    }
  }
}
