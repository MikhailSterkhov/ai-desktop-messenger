package ru.itzstonlex.desktop.itzmsg.form.type.feed.controller;

import javafx.collections.ObservableList;
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
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.request.ChatBotRequest;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.controller.ControllerConfiguration;
import ru.itzstonlex.desktop.itzmsg.form.observer.ObserveBy;
import ru.itzstonlex.desktop.itzmsg.form.observer.impl.FooterButtonSendClickObserver;
import ru.itzstonlex.desktop.itzmsg.form.observer.impl.FooterMessageInputEnterObserver;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.controller.ChatBotHeaderController.TypingStatus;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.function.FeedFormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.type.message.MessageForm;
import ru.itzstonlex.desktop.itzmsg.form.type.message.function.MessageFormFunctionReleaser.SenderType;

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
  private final ChatBotAssistant chatBotAssistant;

  public BothMessagesReceiveController(AbstractSceneForm<?> form, ChatBotAssistant chatBotAssistant) {
    super(form);
    this.chatBotAssistant = chatBotAssistant;
  }

  @Override
  protected void initNodes(@NonNull ControllerConfiguration configuration) {
    firstMessageAnnotationPanel = configuration.getNode(FIRST_MSG_ANNOTATION);

    messageField = configuration.getNode(MESSAGE_FIELD);
    messagesBox = configuration.getNode(MESSAGES_BOX);

    // only for observe
    sendButton = configuration.getNode(SEND_BUTTON);
  }

  @Override
  protected void configureController() {
    // nothing.
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

  public void addMessage(SenderType senderType, String text) {
    ObservableList<Node> childrenList = messagesBox.getChildren();
    Node messageNode = createMessageNode(senderType, reformatMessage(text));

    addMessageChildren(childrenList, messageNode);

    if (firstMessageAnnotationPanel.isVisible()) {
      firstMessageAnnotationPanel.setVisible(false);
    }
  }
}
