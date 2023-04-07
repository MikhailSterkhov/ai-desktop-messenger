package ru.itzstonlex.desktop.chatbotmessenger.api.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ResourceClasspathScanner {

  public Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
    System.out.println("[ResourceClasspathScanner] Scanning package: " + packageName);

    try (Resource resource = ResourceFactory.openClasspath(packageName.replaceAll("[.]", "/"));
        InputStream inputStream = resource.toLocalInputStream()) {

      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader reader = new BufferedReader(inputStreamReader);

      return reader.lines()
          .filter(line -> line.endsWith(".class"))
          .map(line -> getClass(line, packageName))
          .collect(Collectors.toSet());

    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private Class<?> getClass(String className, String packageName) {
    try {
      return Class.forName(packageName + "."
          + className.substring(0, className.lastIndexOf('.')));
    } catch (ClassNotFoundException exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
