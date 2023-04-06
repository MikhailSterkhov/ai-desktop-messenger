package ru.itzstonlex.desktop.itzmsg.form.type.feed;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ru.itzstonlex.desktop.itzmsg.chatbot.ChatBotAssistant;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.ApplicationFormKeys;
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
