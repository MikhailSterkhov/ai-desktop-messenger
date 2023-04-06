package ru.itzstonlex.desktop.itzmsg.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourcesUtils {

  private static final String PATH_RESOLVE_FORMAT = "/%s";

  @RequiredArgsConstructor
  public enum ResourcesGroup {

    IMAGES("img"),
    JAVAFX("javafx"),
    SOUNDS("sound"),
    ;

    private final String path;
  }

  @RequiredArgsConstructor
  public enum ResourcesDirection {

    IMAGES_AVATAR("avatar"),
    IMAGES_WALLPAPER("wallpaper"),

    JAVAFX_STYLESHEETS("css"),
    JAVAFX_MARKDOWNS("fxml"),
    ;

    private final String path;
  }

  public String createResourcePath(@NonNull ResourcesGroup resourcesGroup, @NonNull ResourcesDirection resourcesDirection) {
    return String.format(PATH_RESOLVE_FORMAT + PATH_RESOLVE_FORMAT, resourcesGroup.path, resourcesDirection.path);
  }

  public String createResourcePath(@NonNull ResourcesGroup resourcesGroup, @NonNull ResourcesDirection resourcesDirection, @NonNull String filename) {
    return createResourcePath(resourcesGroup, resourcesDirection) + String.format(PATH_RESOLVE_FORMAT, filename);
  }

  public URL toClasspathResourceUrl(String resourcePath) {
    return ResourcesUtils.class.getResource(resourcePath);
  }

  public InputStream toClasspathResourceInputStream(String resourcePath) {
    return ResourcesUtils.class.getResourceAsStream(resourcePath);
  }

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
