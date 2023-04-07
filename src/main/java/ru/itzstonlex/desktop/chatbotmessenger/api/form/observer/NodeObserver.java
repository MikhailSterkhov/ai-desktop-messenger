package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer;

import javafx.scene.Node;
import lombok.NonNull;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;

public interface NodeObserver<T extends AbstractSceneForm<?>> {

  ApplicationFormKeys.Key getApplicationFormKey();

  void beginObserving();

  T getForm();

  void setForm(T form);

  Node getComponent();

  void setComponent(@NonNull Node node);
}
