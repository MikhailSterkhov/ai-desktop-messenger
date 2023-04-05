package ru.itzstonlex.desktop.itzmsg.chatbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.NonNull;
import ru.itzstonlex.desktop.itzmsg.chatbot.exclusion.ChatBotKeywordExclusion;
import ru.itzstonlex.desktop.itzmsg.chatbot.exclusion.ChatBotKeywordExclusionService;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.request.ChatBotRequest;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.response.ChatBotResponseList;
import ru.itzstonlex.desktop.itzmsg.chatbot.type.response.ChatBotResponse;

public class ChatBotAssistant {

  private final List<ChatBotExceptionHandler> exceptionHandlersList =
      Collections.synchronizedList(new ArrayList<>());

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  private final ChatBotKeywordExclusionService keywordExclusionService =
      new ChatBotKeywordExclusionService();

  public CompletableFuture<ChatBotResponseList> completeFullSuggestionsList(@NonNull ChatBotRequest chatBotRequest) {
    return CompletableFuture.supplyAsync(() -> {

      ChatBotKeywordExclusion exclusion = keywordExclusionService.makeExclusion(chatBotRequest.getPrompt());
      return exclusion.configureSuggestions();

    }, executorService);
  }

  public CompletableFuture<ChatBotResponse> completeBestSuggestion(@NonNull ChatBotRequest chatBotRequest) {
    CompletableFuture<ChatBotResponseList> requestFuture = completeFullSuggestionsList(
        chatBotRequest);

    requestFuture.exceptionally(this::fireExceptionHandlers);
    return CompletableFuture.supplyAsync(() -> {

      ChatBotResponseList suggestions = requestFuture.join();
      return suggestions.getBestSuggestion();

    }, executorService);
  }

  private ChatBotResponseList fireExceptionHandlers(@NonNull Throwable throwable) {
    for (ChatBotExceptionHandler chatBotExceptionHandler : exceptionHandlersList) {
      chatBotExceptionHandler.onThrow(throwable);
    }

    return null;
  }

  public void addExceptionHandler(@NonNull ChatBotExceptionHandler chatBotExceptionHandler) {
    exceptionHandlersList.add(chatBotExceptionHandler);
  }
}
