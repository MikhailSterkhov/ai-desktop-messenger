package ru.itzstonlex.desktop.chatbotmessenger.loading;

import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.FormLoader;
import ru.itzstonlex.desktop.chatbotmessenger.api.sound.SoundPlayer;

public enum ApplicationServices {

  FORMS {
    @Override
    public void load(AsyncServicesLoader loader) {
      loader.sync(() -> {

        try {
          final ApplicationFormKeys.Key[] formsKeysArray = AsyncServicesLoader.FORMS_TO_LOAD;
          final FormLoader formLoader = loader.getFormLoader();

          formLoader.configureFormsCaches(formsKeysArray);
        }
        catch (IOException exception) {
          loader.getFormManipulator().shorError(exception);
        }
      });
    }
  },

  // OBSERVERS {
  //   @Override
  //   public void load(AsyncServicesLoader loader) {
  //     final FormLoader formLoader = loader.getFormLoader();
  //     final Map<ApplicationFormKeys.Key, AbstractSceneForm<?>> initializedFormsMap = formLoader.getApplicationFormsMap();

  //     initializedFormsMap.forEach((formKey, abstractForm) -> {

  //       final String abstractFormClassName = abstractForm.getClass().getName();

  //       loader.debug("Load %s", abstractFormClassName);
  //       loader.sync(() -> {

  //         LoadingFormManipulator formManipulator = loader.getFormManipulator();
  //         formManipulator.addLore("Load " + abstractFormClassName);
  //       });

  //       loader.getFormLoader().injectAllObservers(abstractForm);
  //     });
  //   }
  // },

  CHATBOT {
    @Override
    public void load(AsyncServicesLoader loader) {
      // nothing.
    }
  },

  SOUNDS {
    @Override
    public void load(AsyncServicesLoader loader) {
      try {
        SoundPlayer.prepareSoundsFiles();
      }
      catch (IOException | JavaLayerException exception) {
        loader.getFormManipulator().shorError(exception);
      }
    }
  },

  GENERAL {
    @Override
    public void load(AsyncServicesLoader loader) {
      loader.debug("Application parts was successful loaded");
      loader.debug("Redirection to feedback page...");
    }
  },
  ;

  public abstract void load(AsyncServicesLoader loader);
}
