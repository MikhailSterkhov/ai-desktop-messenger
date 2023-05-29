package ru.itzstonlex.desktop.chatbotmessenger.api.google.speech;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class MicrophoneLineBuffer implements Runnable {

  private static final int BYTES_PER_BUFFER = 6400;

  private final AudioFormat audioFormat;
  private final TargetDataLine targetDataLine;
  private final BlockingQueue<byte[]> sharedQueue = new LinkedBlockingQueue<>();

  private void share(byte[] data) {
    boolean isLineOpen = targetDataLine.isOpen();
    int numBytesRead = targetDataLine.read(data, 0, data.length);

    if ((numBytesRead <= 0) && isLineOpen)
      return;

    try {
      sharedQueue.put(data.clone());
    }
    catch (InterruptedException exception) {
      System.out.println("[MicBuffer] Input buffering interrupted: " + exception.getMessage());
    }
  }

  @Override
  public void run() {
    targetDataLine.start();

    byte[] data = new byte[BYTES_PER_BUFFER];

    while (targetDataLine.isOpen()) {
      share(data);
    }
  }
}