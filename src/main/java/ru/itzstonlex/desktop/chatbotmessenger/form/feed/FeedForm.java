package ru.itzstonlex.desktop.chatbotmessenger.form.feed;

import ru.itzstonlex.desktop.chatbotmessenger.api.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.function.FormFunctionReleaser;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase.FormUsecase;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase.FormUsecaseKeys;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.BothMessagesReceiveController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.controller.ChatBotHeaderController;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.function.FeedFormFunctionReleaser;
import ru.itzstonlex.desktop.chatbotmessenger.form.feed.view.FeedFormFrontView;

public final class FeedForm extends AbstractSceneForm<FeedFormFrontView> {

  private static final double WIDTH_CONST = 675;
  private static final double HEIGHT_CONST = 870;

  public FeedForm() {
    super(ApplicationFormKeys.FEED);
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

    addController(new ChatBotHeaderController(this));
    addController(new BothMessagesReceiveController(this, chatBotAssistant));
  }
}
