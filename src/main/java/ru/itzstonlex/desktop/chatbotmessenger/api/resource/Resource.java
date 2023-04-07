package ru.itzstonlex.desktop.chatbotmessenger.api.resource;

import java.io.Closeable;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public interface Resource extends Closeable {

  String getName();

  String getPathName();

  Path toPath();

  URL toURL();

  InputStream toInputStream();

  InputStream toLocalInputStream();
}
