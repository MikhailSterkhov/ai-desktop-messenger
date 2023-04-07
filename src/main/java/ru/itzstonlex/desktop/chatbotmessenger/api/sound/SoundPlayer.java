package ru.itzstonlex.desktop.chatbotmessenger.api.sound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.Resource;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.ResourceFactory;
import ru.itzstonlex.desktop.chatbotmessenger.api.resource.type.ResourceGroup;

@UtilityClass
public class SoundPlayer {

  @Getter
  @RequiredArgsConstructor
  class SoundInputStreamWrapper {

    private final ByteArrayInputStream inputStream;
    private Player player;

    void updatePlayer() throws JavaLayerException {
      inputStream.reset();
      player = new Player(inputStream);
    }
  }

  private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
  private final String DEFAULT_FILE_FORMAT = ".mp3";

  private final Map<String, SoundInputStreamWrapper> CACHED_SOUND_PLAYERS_MAP = new ConcurrentHashMap<>();

  public void prepareSoundsFiles() throws JavaLayerException, IOException {
    final GroupedSound[] groupedSoundsArray = GroupedSound.values();
    for (GroupedSound groupedSound : groupedSoundsArray) {

      SoundGroup group = groupedSound.getGroup();
      Sound sound = groupedSound.getSound();

      String filename = (sound.getFilePath() + DEFAULT_FILE_FORMAT);
      String resourcePath = ResourceFactory.createPath(ResourceGroup.SOUNDS, group.getFolderPath(), filename);

      byte[] resourceBytesArray;

      try (Resource soundResource = ResourceFactory.openClasspath(resourcePath);
          InputStream resourceInputStream = soundResource.toLocalInputStream()) {

        resourceBytesArray = new byte[resourceInputStream.available()];

        //noinspection ResultOfMethodCallIgnored
        resourceInputStream.read(resourceBytesArray);
      }

      ByteArrayInputStream inputStream = new ByteArrayInputStream(resourceBytesArray);

      SoundInputStreamWrapper soundInputStreamWrapper = new SoundInputStreamWrapper(inputStream);
      soundInputStreamWrapper.updatePlayer();

      CACHED_SOUND_PLAYERS_MAP.put(groupedSound.toString(), soundInputStreamWrapper);
    }
  }

  public void playAsynchronous(@NonNull SoundGroup group, @NonNull Sound sound) {
    playClasspathResourceAsynchronous(formatSoundName(group, sound));
  }

  public void playAsynchronous(@NonNull GroupedSound groupedSound) {
    playClasspathResourceAsynchronous(groupedSound.toString());
  }

  private void playClasspathResourceAsynchronous(@NonNull String formattedSoundName) {
    EXECUTOR.execute(() -> {

      try {
        SoundInputStreamWrapper soundInputStream = CACHED_SOUND_PLAYERS_MAP.get(formattedSoundName);
        Player player = soundInputStream.getPlayer();

        if (player != null) {

          player.play();
          player.close();

          soundInputStream.updatePlayer();
        }

      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });
  }

  public String formatSoundName(@NonNull SoundGroup group, @NonNull Sound sound) {
    return (group.getFolderPath() + ":" + sound.getFilePath());
  }
}
