package ru.itzstonlex.desktop.itzmsg.loading;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.Node;
import lombok.SneakyThrows;
import ru.itzstonlex.desktop.itzmsg.form.AbstractSceneForm;
import ru.itzstonlex.desktop.itzmsg.form.ApplicationFormKeys;
import ru.itzstonlex.desktop.itzmsg.form.FormLoader;
import ru.itzstonlex.desktop.itzmsg.form.controller.AbstractComponentController;
import ru.itzstonlex.desktop.itzmsg.form.observer.NodeObserver;
import ru.itzstonlex.desktop.itzmsg.form.observer.NodeObserverConfigurable;
import ru.itzstonlex.desktop.itzmsg.form.observer.ObserveBy;
import ru.itzstonlex.desktop.itzmsg.utility.ResourcesUtils;

public enum ApplicationServices {

  FORMS {
    @Override
    public void load(AsyncServicesLoader loader) {
      try {
        final FormLoader formLoader = loader.getFormLoader();
        formLoader.configureFormsCaches(AsyncServicesLoader.FORMS_TO_LOAD);
      }
      catch (IOException exception) {
        loader.getFormManipulator().shorError(exception);
      }
    }
  },

  OBSERVERS {
    @Override
    public void load(AsyncServicesLoader loader) {
      final FormLoader formLoader = loader.getFormLoader();

      final Map<ApplicationFormKeys.Key, Set<NodeObserver<AbstractComponentController>>> observersMap = loadAllObservers();
      final Map<ApplicationFormKeys.Key, AbstractSceneForm<?>> initializedFormsMap = formLoader.getInitializedFormsMap();

      initializedFormsMap.forEach((formKey, abstractForm) -> {

        final String abstractFormClassName = abstractForm.getClass().getName();
        final Set<NodeObserver<AbstractComponentController>> observerSet = observersMap.get(formKey);

        loader.debug("Load %s", abstractFormClassName);
        loader.sync(() -> {

          final LoadingFormManipulator formManipulator = loader.getFormManipulator();
          formManipulator.addLore("Load " + abstractFormClassName);
        });

        initFormObservers(abstractForm, observerSet);
      });
    }

    @SneakyThrows
    private void initFormObservers(AbstractSceneForm<?> form, Set<NodeObserver<AbstractComponentController>> observers) {
      for (AbstractComponentController controller : form.getComponentControllers()) {
        Class<? extends AbstractComponentController> controllerCls = controller.getClass();

        for (Field field : controllerCls.getDeclaredFields()) {
          ObserveBy annotation = field.getDeclaredAnnotation(ObserveBy.class);
          if (annotation == null || !Node.class.isAssignableFrom(field.getType()))
            continue;

          Class<? extends NodeObserver<?>>[] observersArray = annotation.value();
          List<Class<? extends NodeObserver<?>>> observersList = Arrays.asList(observersArray);

          field.setAccessible(true);

          Node node = ((Node) field.get(controller));
          observers.stream()
              .filter(observer -> observersList.contains(observer.getClass()))
              .findFirst()
              .ifPresent(
                  (nodeObserver) -> {
                    nodeObserver.setController(controller);
                    nodeObserver.setComponent(node);

                    if (nodeObserver instanceof NodeObserverConfigurable) {
                      ((NodeObserverConfigurable) nodeObserver).configure();
                    }

                    field.setAccessible(false);
                    nodeObserver.beginObserving();
                  });
        }
      }
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private Map<ApplicationFormKeys.Key, Set<NodeObserver<AbstractComponentController>>> loadAllObservers() {
      Map<ApplicationFormKeys.Key, Set<NodeObserver<AbstractComponentController>>> map = new HashMap<>();

      Set<Class<?>> allClassesUsingClassLoader = ResourcesUtils.findAllClassesUsingClassLoader(
          "ru.itzstonlex.desktop.itzmsg.form.observer.impl"
      );

      for (Class<?> cls : allClassesUsingClassLoader) {
        if (NodeObserver.class.isAssignableFrom(cls)) {

          NodeObserver<AbstractComponentController> observer = (NodeObserver<AbstractComponentController>) cls.getConstructor().newInstance();
          Set<NodeObserver<AbstractComponentController>> observersSet = map.get(observer.getExtendedFormKey());

          if (observersSet == null) {
            observersSet = new HashSet<>();
          }

          observersSet.add(observer);
          map.put(observer.getExtendedFormKey(), observersSet);
        }
      }

      return map;
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
