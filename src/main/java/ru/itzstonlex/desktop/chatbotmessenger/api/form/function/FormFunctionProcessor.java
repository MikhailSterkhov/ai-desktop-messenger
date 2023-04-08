package ru.itzstonlex.desktop.chatbotmessenger.api.form.function;

@FunctionalInterface
public interface FormFunctionProcessor {

  void process(Object... values) throws Exception;
}
