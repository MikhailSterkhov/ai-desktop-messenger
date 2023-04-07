package ru.itzstonlex.desktop.chatbotmessenger.api.utility;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.ResourcesUtils.ResourcesDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.ResourcesUtils.ResourcesGroup;

@UtilityClass
public class ImageViewUtils {

  @RequiredArgsConstructor
  public enum AvatarType {

    CHAT_BOT("chatbot.png"),
    USER("user.png"),
    ;

    private final String filename;
  }

  public void setImage(@NonNull ImageView imageView, @NonNull String resourcePath) {
    InputStream resourceAsStream = ResourcesUtils.toClasspathResourceInputStream(
        ResourcesUtils.createResourcePath(ResourcesGroup.IMAGES, ResourcesDirection.IMAGES_AVATAR, resourcePath)
    );

    if (resourceAsStream != null) {

      Image image = new Image(resourceAsStream);
      imageView.setImage(image);
    }
  }

  public void setAvatarImage(@NonNull ImageView imageView, @NonNull AvatarType avatarType) {
    setImage(imageView, avatarType.filename);
  }
}
