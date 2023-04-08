package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObserveBy {

  Class<? extends NodeObserver<? extends AbstractSceneForm<?>>>[] value();
}
