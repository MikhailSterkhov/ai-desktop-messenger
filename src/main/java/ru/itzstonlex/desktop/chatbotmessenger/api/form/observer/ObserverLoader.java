package ru.itzstonlex.desktop.chatbotmessenger.api.form.observer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;
import lombok.SneakyThrows;
import net.nodeson.NodesonUnsafe;
import ru.itzstonlex.desktop.chatbotmessenger.api.form.AbstractSceneForm;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.scanner.ResourceClasspathScannerResponse;

public final class ObserverLoader {

  @SuppressWarnings("unchecked")
  @SneakyThrows
  private void injectObservers(AbstractSceneForm<?> form, Collection<?> injectInstances) {
    ResourceClasspathScannerResponse response = new ResourceClasspathScannerResponse(null,
        injectInstances.stream().map(Object::getClass).collect(Collectors.toSet()));

    for (Field field : response.getAccessedFieldsByAnnotation(Node.class, ObserveBy.class)) {
      Class<? extends NodeObserver<AbstractSceneForm<?>>>[] observersArray =
          (Class<? extends NodeObserver<AbstractSceneForm<?>>>[]) field.getDeclaredAnnotation(ObserveBy.class).value();

      List<Class<? extends NodeObserver<AbstractSceneForm<?>>>> observersList = Arrays.asList(observersArray);
      Node node = ((Node) field.get(getInstanceByClass(injectInstances, field.getDeclaringClass())));

      observeNode(form, node, observersList.stream().map(NodesonUnsafe::allocate).collect(Collectors.toSet()));
    }
  }

  private void observeNode(
      AbstractSceneForm<?> form,
      Node node,
      Collection<? extends NodeObserver<AbstractSceneForm<?>>> nodeObservers
  ) {
    nodeObservers.stream()
        .findFirst()
        .ifPresent(
            (nodeObserver) -> {
              nodeObserver.setForm(form);
              nodeObserver.setComponent(node);

              if (nodeObserver instanceof NodeObserverConfigurable)
                ((NodeObserverConfigurable) nodeObserver).configure();

              nodeObserver.beginObserving();
            });
  }

  public void injectAllObservers(AbstractSceneForm<?> form) {
    injectObservers(form, form.getComponentControllers());
    injectObservers(form, Collections.singletonList(form));
    injectObservers(form, Collections.singletonList(form.getView()));
  }

  // utility method
  private Object getInstanceByClass(Collection<?> objects, Class<?> searchType) {
    return objects.stream().filter(obj -> obj.getClass() == searchType).findFirst().orElse(null);
  }
}
