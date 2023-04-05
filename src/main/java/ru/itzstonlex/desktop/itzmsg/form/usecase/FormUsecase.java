package ru.itzstonlex.desktop.itzmsg.form.usecase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FormUsecase {

  private final Map<String, Object> usecaseDataMap = new HashMap<>();

  public void set(String key, Object value) {
    usecaseDataMap.put(key, value);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void add(String key, Object value) {
    Object obj = usecaseDataMap.get(key);

    if (obj instanceof List) {
      ((List) obj).add(value);
    } else {
      List list = new ArrayList<>();

      list.add(obj);
      list.add(value);

      set(key, list);
    }
  }

  public void clearKey(String key) {
    usecaseDataMap.remove(key);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key) {
    return (T) usecaseDataMap.get(key);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key, T def) {
    return (T) usecaseDataMap.getOrDefault(key, def);
  }

  public List<Object> getAsList(String key) {
    Object obj = usecaseDataMap.get(key);

    if (obj instanceof Collection) {
      return new ArrayList<>((Collection<?>) obj);
    } else {
      return Collections.singletonList(obj);
    }
  }

  public boolean getAsFlag(String key) {
    return usecaseDataMap.containsKey(key) && ((Boolean) get(key));
  }

}
