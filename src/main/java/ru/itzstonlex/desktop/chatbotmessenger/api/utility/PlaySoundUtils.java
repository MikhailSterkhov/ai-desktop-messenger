package ru.itzstonlex.desktop.chatbotmessenger.api.utility;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javazoom.jl.player.Player;
import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.ResourcesUtils.ResourcesDirection;
import ru.itzstonlex.desktop.chatbotmessenger.api.utility.ResourcesUtils.ResourcesGroup;

@UtilityClass
public class PlaySoundUtils {

  private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
  private final String DEFAULT_FILE_FORMAT = ".mp3";

  public void playLocalMP3(String filename) {
    String resourcePath = ResourcesUtils.createResourcePath(ResourcesGroup.SOUNDS, ResourcesDirection.SOUNDS_MESSAGE,
        filename + DEFAULT_FILE_FORMAT);

    EXECUTOR.submit(() -> {
      try (InputStream inputStream = ResourcesUtils.toClasspathResourceInputStream(resourcePath)) {

        Player player = new Player(inputStream);

        player.play();
        player.close();
      }
      catch (Exception exception) {
        exception.printStackTrace();
      }
    });
  }

  public void playPickupSound() {
    playLocalMP3("pickup");
  }

}
