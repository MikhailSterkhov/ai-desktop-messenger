package ru.itzstonlex.desktop.chatbotmessenger.api.usecase;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

public interface IUsecaseKeysStorage {

  @Getter
  @ToString
  @EqualsAndHashCode
  @RequiredArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  class Key
  {
    String name;
  }
}
