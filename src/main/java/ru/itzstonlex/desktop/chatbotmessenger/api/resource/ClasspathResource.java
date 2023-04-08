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

    localInputStream = getClass().getResourceAsStream(path);
    if (localInputStream == null) {
      localInputStream = getClass().getResourceAsStream(PATH_SEPARATOR + path);
    }

    inputStream = classLoader.getResourceAsStream(path);
    if (inputStream == null) {
      inputStream = classLoader.getResourceAsStream(PATH_SEPARATOR + path);
    }

    url = classLoader.getResource(path);
    if (url == null) {
      url = classLoader.getResource(PATH_SEPARATOR + path);
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
