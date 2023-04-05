package ru.itzstonlex.desktop.itzmsg.type.feed.controller;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.request.ChatBotRequest;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.FormComponentsMap;
import ru.itzstonlex.desktop.itzmsg.form.SceneViewTable;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.ObserveBy;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.impl.FooterMessageInputEnterObserver;
import ru.itzstonlex.desktop.itzmsg.form.controller.observer.impl.FooterButtonSendClickObserver;
import ru.itzstonlex.desktop.itzmsg.form.controller.subaction.ControllerSubActionStorage;
import ru.itzstonlex.desktop.itzmsg.type.feed.FeedForm;
import ru.itzstonlex.desktop.itzmsg.type.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.itzmsg.type.feed.subaction.FeedSubactionsStorage;
import ru.itzstonlex.desktop.itzmsg.type.message.MessageForm;

@RequiredArgsConstructor
public final class BothMessagesReceiveController extends AbstractComponentController {

  public static final String MESSAGE_FIELD = "message_field";
  public static final String MESSAGES_BOX = "messages_box";
  public static final String SEND_BUTTON = "send_button";
  public static final String FIRST_MSG_ANNOTATION = "first_msg_annotation";

  private static final Background TRANSPARENT_BACKGROUND = new Background(new BackgroundFill(Color.TRANSPARENT, null, null));

  @ObserveBy(FooterMessageInputEnterObserver.class)
  private TextField messageField;

  @ObserveBy(FooterButtonSendClickObserver.class)
  private Button sendButton;

  private VBox messagesBox;

  private AnchorPane firstMessageAnnotationPanel;

  @Getter
  private final ChatBotHeaderController botUserController;

  @Getter
  private final ChatBotAssistant chatBotAssistant;

  @Getter
  private final FeedForm feedForm;

  @Override
  protected ControllerSubActionStorage<?> getSubActionsStorage() {
    return new FeedSubactionsStorage();
  }

  @Override
  protected void initNodes(@NonNull FormComponentsMap map) {
    firstMessageAnnotationPanel = map.getNode(FIRST_MSG_ANNOTATION);

    messageField = map.getNode(MESSAGE_FIELD);
    messagesBox = map.getNode(MESSAGES_BOX);

    // only for observe
    sendButton = map.getNode(SEND_BUTTON);
  }

  @Override
  protected void configureController() {
    configureMessagesBox();
    configureMessageField();
  }

  public void onMessageReceive(@NonNull String receivedMessage) {

    // send question
    callSubaction(FeedSubactionsStorage.SEND, receivedMessage);
    botUserController.setTypingStatus(TypingStatus.TYPING);

    // send answer
    ChatBotRequest chatBotRequest = new ChatBotRequest(receivedMessage);
    chatBotAssistant.completeBestSuggestion(chatBotRequest)
        .thenAcceptAsync(response -> callSubaction(FeedSubactionsStorage.REPLY, response.getMessageText()));
  }

  @SneakyThrows
  private Node createMessageNode(MessageForm.SenderType senderType, String msg) {
    AbstractSceneForm abstractSceneForm = feedForm.getSceneLoader()
        .loadUncachedSceneForm(SceneViewTable.MESSAGE);

    MessageForm messageForm = ((MessageForm) abstractSceneForm);
    messageForm.updateMessageText(senderType, msg);

    return messageForm.getJavafxNode();
  }

  private Node createWrapper(Node node) {
    final Pane wrapper = new Pane();

    wrapper.setMinHeight(50);
    wrapper.setPrefHeight(Region.USE_COMPUTED_SIZE);
    wrapper.setMaxHeight(Region.USE_COMPUTED_SIZE);

    wrapper.setNodeOrientation(node.getNodeOrientation());
    wrapper.getChildren().add(node);

    return wrapper;
  }

  private Node createSplitterPane() {
    final Pane emptySplitterPane = new Pane();
    emptySplitterPane.setPrefHeight(10);
    return emptySplitterPane;
  }

  private void addMessageChildren(ObservableList<Node> childrenList, Node messageNode) {
    Node wrapper = createWrapper(messageNode);
    Node splitterPane = createSplitterPane();

    childrenList.add(wrapper);
    childrenList.add(splitterPane);
  }

  private String reformatMessage(String text) {
    text = text.replace("<br>", "\n");
    return text;
  }

  public void addMessage(MessageForm.SenderType senderType, String text) {
    ObservableList<Node> childrenList = messagesBox.getChildren();
    Node messageNode = createMessageNode(senderType, reformatMessage(text));

    addMessageChildren(childrenList, messageNode);

    if (firstMessageAnnotationPanel.isVisible()) {
      firstMessageAnnotationPanel.setVisible(false);
    }
  }

  private void configureMessagesBox() {
    messagesBox.setMinHeight(Region.USE_COMPUTED_SIZE);
    messagesBox.setMaxHeight(Region.USE_COMPUTED_SIZE);

    messagesBox.setBackground(TRANSPARENT_BACKGROUND);
  }

  private void configureMessageField() {
    messageField.setFont(Font.font(18));

    messageField.setMaxWidth(Region.USE_COMPUTED_SIZE);
    messageField.setMaxHeight(Region.USE_COMPUTED_SIZE);

    messageField.setCursor(Cursor.TEXT);
  }
}
