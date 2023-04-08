package ru.itzstonlex.desktop.chatbotmessenger.api.resource.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;

public final class ResourceClasspathScanner {

  public static final String BASE_PACKAGE_NAME = "ru.itzstonlex.desktop.chatbotmessenger";

  public String getPackageName(String resolve) {
    return BASE_PACKAGE_NAME + "." + resolve;
  }

  public ResourceClasspathScannerResponse findAllClassesUsingClassLoader(String packageName) {
    System.out.println("[ResourceClasspathScanner] Scanning package: " + packageName);

    try (Resource resource = ResourceFactory.openClasspath(packageName.replaceAll("[.]", "/"));
        InputStream inputStream = resource.toLocalInputStream()) {

      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader reader = new BufferedReader(inputStreamReader);

      Set<Class<?>> classesResponse = reader.lines()
          .filter(line -> line.endsWith(".class"))
          .map(line -> getClass(line, packageName))
          .collect(Collectors.toSet());

      return new ResourceClasspathScannerResponse(packageName, classesResponse);
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
