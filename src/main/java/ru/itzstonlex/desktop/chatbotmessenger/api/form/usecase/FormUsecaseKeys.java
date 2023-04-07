package ru.itzstonlex.desktop.chatbotmessenger.api.form.usecase;

import ru.itzstonlex.desktop.chatbotmessenger.api.usecase.IUsecaseKeysStorage;

public interface FormUsecaseKeys extends IUsecaseKeysStorage {

  Key FORWARD_FORM = new Key("Forward");
  Key BACKWARD_FORM = new Key("Backward");

  Key CUSTOM_HEIGHT = new Key("SceneHeight");
  Key CUSTOM_WIDTH = new Key("SceneWidth");

  // object instances.
  Key SCENE_LOADER_OBJ = new Key("SceneLoader");

  // flags.
  Key FRAME_RESIZABLE_DISABLE_FLAG = new Key("FrameResizableDisable");
}
