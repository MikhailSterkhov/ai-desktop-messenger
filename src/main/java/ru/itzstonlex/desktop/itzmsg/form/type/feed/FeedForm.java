package ru.itzstonlex.desktop.itzmsg.form.type.feed;

import ru.itzstonlex.desktop.itzmsg.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.FormKeys;
import ru.itzstonlex.desktop.itzmsg.form.function.FormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.itzmsg.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.controller.ChatBotHeaderController;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.function.FeedFormFunctionReleaser;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.view.FeedFormFrontView;
import ru.itzstonlex.desktop.itzmsg.form.type.feed.view.FeedFormFrontViewConfiguration;

public final class FeedForm extends AbstractSceneForm<FeedFormFrontView> {

  private static final double WIDTH_CONST = 675;
  private static final double HEIGHT_CONST = 870;

  public FeedForm() {
    super(FormKeys.FEED);
  }

  @Override
  public FeedFormFrontView newFrontView() {
    return new FeedFormFrontView(this);
  }

  @Override
  public FormFunctionReleaser<?> newFunctionReleaser() {
    return new FeedFormFunctionReleaser();
  }

  @Override
  public void initializeUsecase(FormUsecase usecase) {
    usecase.set(FormUsecaseKeys.FRAME_RESIZABLE_DISABLE_FLAG, true);

    usecase.set(FormUsecaseKeys.CUSTOM_WIDTH, WIDTH_CONST);
    usecase.set(FormUsecaseKeys.CUSTOM_HEIGHT, HEIGHT_CONST);
  }

  @Override
  public void initializeControllers() {
    ChatBotAssistant chatBotAssistant = new ChatBotAssistant();
    chatBotAssistant.addExceptionHandler(Throwable::printStackTrace);

    ChatBotHeaderController botUserController = new ChatBotHeaderController(this);
    botUserController
        .with(ChatBotHeaderController.USER_NAME, view.find(FeedFormFrontViewConfiguration.USERNAME_LABEL))
        .with(ChatBotHeaderController.USER_STATUS, view.find(FeedFormFrontViewConfiguration.USER_STATUS_LABEL));

    BothMessagesReceiveController messagesController = new BothMessagesReceiveController(this, chatBotAssistant);
    messagesController
        .with(BothMessagesReceiveController.MESSAGE_FIELD, view.find(FeedFormFrontViewConfiguration.INPUT_MESSAGE_FIELD))
        .with(BothMessagesReceiveController.MESSAGES_BOX, view.find(FeedFormFrontViewConfiguration.LIST_MESSAGES_BOX))
        .with(BothMessagesReceiveController.SEND_BUTTON, view.find(FeedFormFrontViewConfiguration.MESSAGE_SEND_BUTTON))
        .with(BothMessagesReceiveController.FIRST_MSG_ANNOTATION, view.find(FeedFormFrontViewConfiguration.NO_MESSAGES_PANEL));

    addController(botUserController);
    addController(messagesController);
  }
}
