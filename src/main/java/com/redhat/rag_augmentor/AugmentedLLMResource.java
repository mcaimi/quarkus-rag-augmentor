package com.redhat.rag_augmentor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import io.quarkiverse.langchain4j.ChatMemoryRemover;
import org.eclipse.microprofile.context.ManagedExecutor;


@ApplicationScoped
@ServerEndpoint("/llm_augmented")
public class AugmentedLLMResource {

  @Inject
  AugmentedOllamaService augmentedOllamaService;

  @Inject
  ManagedExecutor asyncExecutor;

  @OnOpen
  public void onOpen(Session session) {
      asyncExecutor.execute(() -> {
          String response = augmentedOllamaService.chat(session, "Hello");
          try {
              session.getBasicRemote().sendText(response);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      });
  }

  @OnClose
  void onClose(Session session) {
      ChatMemoryRemover.remove(augmentedOllamaService, session);
  }

  @OnMessage
  public void onMessage(String message, Session session) {
      asyncExecutor.execute(() -> {
          String response = augmentedOllamaService.chat(session, message);
          try {
              session.getBasicRemote().sendText(response);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      });

  }
}
