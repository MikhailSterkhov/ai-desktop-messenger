package ru.itzstonlex.desktop.itzmsg.utility;

import java.io.File;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageViewUtils {

  @RequiredArgsConstructor
  public enum AvatarType {

    CHAT_BOT("/img/avatar/chatbot.png"),
    USER("/img/avatar/user.png"),
    ;

    private final String resourcePath;
  }

  public void setImage(@NonNull ImageView imageView, @NonNull String resourcePath) {
    InputStream resourceAsStream = ImageViewUtils.class.getResourceAsStream(resourcePath
        .replace("/", File.separator));

    if (resourceAsStream != null) {

      Image image = new Image(resourceAsStream);
      imageView.setImage(image);
    }
  }

  public void setAvatarImage(@NonNull ImageView imageView, @NonNull AvatarType avatarType) {
    setImage(imageView, avatarType.resourcePath);
  }
}
