package com.redhat.rag_augmentor;

import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@ApplicationScoped // required for websocket interface
@RegisterAiService(modelName="llm_interactive_model")
public interface DirectOllamaService {


    @SystemMessage("""
    You are an experienced network engineer tasked with responding queries related to RFC documents.
    You know a lot about RFCs but if you do not know the answer to a question, please respond that you do not know.
    Prioritize responses in the same language as the request.
    """)

    @UserMessage("""
    {userMsg}
    """)
    String chat(@MemoryId Object session, String userMsg);
}
