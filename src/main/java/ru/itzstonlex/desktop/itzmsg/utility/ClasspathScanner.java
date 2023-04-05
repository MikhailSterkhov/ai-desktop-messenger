package ru.itzstonlex.desktop.itzmsg.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClasspathScanner {

  public Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
    System.out.println("[ClasspathScanner] Scanning package: " + packageName);

    InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]",
        File.separator));

    InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(stream));
    BufferedReader reader = new BufferedReader(inputStreamReader);

    return reader.lines()
        .filter(line -> line.endsWith(".class"))
        .map(line -> getClass(line, packageName))
        .collect(Collectors.toSet());
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