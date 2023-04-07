package ru.itzstonlex.desktop.chatbotmessenger.api.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import lombok.NonNull;

public class ClasspathResource extends AbstractResource {

  private final ClassLoader classLoader;

  private URL url;
  private InputStream inputStream, localInputStream;

  ClasspathResource(@NonNull ClassLoader classLoader, @NonNull String pathName) {
    super(pathName);
    this.classLoader = classLoader;

    fillCloseableInstances();
  }

  ClasspathResource(@NonNull String pathName) {
    this(ClassLoader.getSystemClassLoader(), pathName);
  }

  private void fillCloseableInstances() {
    String path = getPathName();

    try {
      this.localInputStream = getClass().getResourceAsStream(path);
      if (localInputStream == null) {
        this.localInputStream = getClass().getResourceAsStream(PATH_SEPARATOR + path);
      }
    }
    catch (Throwable ignored) {
      // ignored.
    }
    try {
      this.inputStream = classLoader.getResourceAsStream(path);
      if (inputStream == null) {
        this.inputStream = classLoader.getResourceAsStream(PATH_SEPARATOR + path);
      }
    }
    catch (Throwable ignored) {
      // ignored.
    }
    try {
      this.url = classLoader.getResource(path);
      if (url == null) {
        this.url = classLoader.getResource(PATH_SEPARATOR + path);
      }
    }
    catch (Throwable ignored) {
      // ignored.
    }
  }

  @Override
  public URL toURL() {
    return url;
  }

  @Override
  public InputStream toInputStream() {
    return inputStream;
  }

  @Override
  public InputStream toLocalInputStream() {
    return localInputStream;
  }

  @Override
  public void close() throws IOException {
    if (inputStream != null) {
      inputStream.close();
    }
  }
}
