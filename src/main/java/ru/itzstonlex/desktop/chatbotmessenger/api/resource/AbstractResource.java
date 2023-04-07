package ru.itzstonlex.desktop.chatbotmessenger.api.resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class AbstractResource implements Resource {

  public static final String PATH_SEPARATOR = "/";

  private final String name;
  private final String pathName;

  public AbstractResource(@NonNull String pathName) {
    this.pathName = pathName.replace(File.separator, PATH_SEPARATOR);
    this.name = this.pathName.substring(pathName.lastIndexOf(PATH_SEPARATOR));
  }

  @Override
  public Path toPath() {
    return Paths.get(pathName);
  }
}
