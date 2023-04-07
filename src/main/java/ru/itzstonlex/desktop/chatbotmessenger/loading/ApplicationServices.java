package ru.itzstonlex.desktop.chatbotmessenger.loading;

import java.io.IOException;
import java.util.Map;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.FormLoader;

public enum ApplicationServices {

  FORMS {
    @Override
    public void load(AsyncServicesLoader loader) {
      loader.sync(() -> {

        try {
          final FormLoader formLoader = loader.getFormLoader();
          formLoader.configureFormsCaches(AsyncServicesLoader.FORMS_TO_LOAD);
        }
        catch (IOException exception) {
          loader.getFormManipulator().shorError(exception);
        }
      });
    }
  },

  OBSERVERS {
    @Override
    public void load(AsyncServicesLoader loader) {
      final FormLoader formLoader = loader.getFormLoader();
      final Map<ApplicationFormKeys.Key, AbstractSceneForm<?>> initializedFormsMap = formLoader.getApplicationFormsMap();

      initializedFormsMap.forEach((formKey, abstractForm) -> {

        final String abstractFormClassName = abstractForm.getClass().getName();

        loader.debug("Load %s", abstractFormClassName);
        loader.sync(() -> {

          LoadingFormManipulator formManipulator = loader.getFormManipulator();
          formManipulator.addLore("Load " + abstractFormClassName);
        });

        loader.getFormLoader().injectAllObservers(abstractForm);
      });
    }
  },

  CHATBOT {
    @Override
    public void load(AsyncServicesLoader loader) {
      // nothing.
    }
  },

  GENERAL {
    @Override
    public void load(AsyncServicesLoader loader) {
      loader.sync(() -> {

        final LoadingFormManipulator formManipulator = loader.getFormManipulator();
        formManipulator.addLore("[Done]");

        final FormLoader formLoader = loader.getFormLoader();
        formLoader.forwardsTo(ApplicationFormKeys.FEED);
      });

      loader.debug("Application parts was successful loaded");
      loader.debug("Redirection to feedback page...");
    }
  },
  ;

  public abstract void load(AsyncServicesLoader loader);
}
