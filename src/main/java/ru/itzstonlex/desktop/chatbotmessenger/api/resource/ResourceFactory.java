package ru.itzstonlex.desktop.chatbotmessenger.api.resource;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;

@UtilityClass
public class ResourceFactory {

  private static final String PATH_RESOLVE_FORMAT = "/%s";

  public String createPath(@NonNull ResourceGroup resourcesGroup, @NonNull String resourcesDirectionPath) {
    return String.format(PATH_RESOLVE_FORMAT + PATH_RESOLVE_FORMAT, resourcesGroup.getPath(), resourcesDirectionPath);
  }

  public String createPath(@NonNull ResourceGroup resourcesGroup, @NonNull ResourceDirection resourcesDirection) {
    return String.format(PATH_RESOLVE_FORMAT + PATH_RESOLVE_FORMAT, resourcesGroup.getPath(), resourcesDirection.getPath());
  }

  public String createPath(@NonNull ResourceGroup resourcesGroup, @NonNull String resourcesDirectionPath, @NonNull String filename) {
    return createPath(resourcesGroup, resourcesDirectionPath) + String.format(PATH_RESOLVE_FORMAT, filename);
  }

  public String createPath(@NonNull ResourceGroup resourcesGroup, @NonNull ResourceDirection resourcesDirection, @NonNull String filename) {
    return createPath(resourcesGroup, resourcesDirection) + String.format(PATH_RESOLVE_FORMAT, filename);
  }

  public Resource openClasspath(@NonNull ClassLoader classLoader, @NonNull String pathName) {
    return new ClasspathResource(classLoader, pathName);
  }

  public Resource openSystemClasspath(@NonNull String pathName) {
    return new ClasspathResource(ClassLoader.getSystemClassLoader(), pathName);
  }

  public Resource openClasspath(@NonNull String pathName) {
    return new ClasspathResource(ResourceFactory.class.getClassLoader(), pathName);
  }
}
