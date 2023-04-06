package ru.itzstonlex.desktop.itzmsg.form.type.feed.view;

import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.view.AbstractFormFrontView;
import ru.itzstonlex.desktop.itzmsg.form.view.FormFrontViewUsecase;

public final class FeedFormFrontView extends AbstractFormFrontView<FeedFormFrontViewConfiguration> {

  public FeedFormFrontView(@NonNull AbstractSceneForm<?> form) {
    super(form, FeedFormFrontViewConfiguration.class);
  }

  @Override
  public void configureDisplay(@NonNull FormFrontViewUsecase<FeedFormFrontViewConfiguration> usecase) {
    // nothing.
  }
}
