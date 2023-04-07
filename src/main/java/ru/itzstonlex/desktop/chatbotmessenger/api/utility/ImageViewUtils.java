package ru.itzstonlex.desktop.chatbotmessenger.api.utility;

import java.io.IOException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;

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
    try (Resource resource = ResourceFactory.openSystemClasspath(ResourceGroup.IMAGES.file(ResourceDirection.IMAGES_AVATAR, resourcePath));
        InputStream inputStream = resource.toLocalInputStream()) {

      if (inputStream != null) {

        Image image = new Image(inputStream);
        imageView.setImage(image);
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public void setAvatarImage(@NonNull ImageView imageView, @NonNull AvatarType avatarType) {
    setImage(imageView, avatarType.filename);
  }
}
