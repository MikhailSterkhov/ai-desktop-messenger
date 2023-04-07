package ru.itzstonlex.desktop.chatbotmessenger.api.usecase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractUsecaseManager<I extends IUsecaseKeysStorage> {

  private final Map<I.Key, Object> map;

  public void set(I.Key key, Object value) {
    map.put(key, value);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void add(I.Key key, Object value) {
    Object obj = map.get(key);

    if (obj instanceof List) {
      ((List) obj).add(value);
    } else {
      List list = new ArrayList<>();

      list.add(obj);
      list.add(value);

      set(key, list);
    }
  }

  public void clearKey(I.Key key) {
    map.remove(key);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(I.Key key) {
    return (T) map.get(key);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(I.Key key, T def) {
    return (T) map.getOrDefault(key, def);
  }

  public List<Object> getAsList(I.Key key) {
    Object obj = map.get(key);

    if (obj instanceof Collection) {
      return new ArrayList<>((Collection<?>) obj);
    } else {
      return Collections.singletonList(obj);
    }
  }

  public boolean getAsFlag(I.Key key) {
    return map.containsKey(key) && ((Boolean) get(key));
  }

}
