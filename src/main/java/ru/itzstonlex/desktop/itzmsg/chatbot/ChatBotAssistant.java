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

  private static final ChatBotResponse DEFAULT_RESPONSE =
      new ChatBotResponse(0, "К сожалению, мне нечем на это ответить.");

  private final List<ChatBotExceptionHandler> exceptionHandlersList =
      Collections.synchronizedList(new ArrayList<>());

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  private final ChatBotKeywordExclusionService keywordExclusionService =
      new ChatBotKeywordExclusionService();

  private ChatBotResponseList getFullSuggestionsList(@NonNull ChatBotRequest chatBotRequest) {
    ChatBotKeywordExclusion exclusion = keywordExclusionService.makeExclusion(chatBotRequest.getPrompt());
    if (exclusion == null)
      return null;
    return exclusion.configureSuggestions();
  }

  private ChatBotResponse getBestSuggestion(@NonNull ChatBotRequest chatBotRequest) {
    ChatBotResponseList suggestions = getFullSuggestionsList(chatBotRequest);
    if (suggestions == null)
      return DEFAULT_RESPONSE;

    ChatBotResponse bestSuggestion = suggestions.getBestSuggestion();
    return (bestSuggestion == null ? DEFAULT_RESPONSE : bestSuggestion);
  }

  public CompletableFuture<ChatBotResponseList> requestFullSuggestionsList(@NonNull ChatBotRequest chatBotRequest) {
    CompletableFuture<ChatBotResponseList> responseListFuture = CompletableFuture.supplyAsync(
        () -> getFullSuggestionsList(chatBotRequest), executorService);

    responseListFuture.exceptionally(throwable -> {

      ChatBotResponse response = fireExceptionHandlers(throwable);
      return new ChatBotResponseList(Collections.singletonList(response));
    });

    return responseListFuture;
  }

  public CompletableFuture<ChatBotResponse> requestBestSuggestion(@NonNull ChatBotRequest chatBotRequest) {
    CompletableFuture<ChatBotResponse> responseFuture = CompletableFuture.supplyAsync(
        () -> getBestSuggestion(chatBotRequest), executorService);

    responseFuture.exceptionally(this::fireExceptionHandlers);
    return responseFuture;
  }

  private ChatBotResponse fireExceptionHandlers(@NonNull Throwable throwable) {
    for (ChatBotExceptionHandler chatBotExceptionHandler : exceptionHandlersList) {
      chatBotExceptionHandler.onThrow(throwable);
    }

    return DEFAULT_RESPONSE;
  }

  public void addExceptionHandler(@NonNull ChatBotExceptionHandler chatBotExceptionHandler) {
    exceptionHandlersList.add(chatBotExceptionHandler);
  }
}
