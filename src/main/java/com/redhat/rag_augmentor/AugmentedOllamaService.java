package com.redhat.rag_augmentor;

import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@Singleton
@RegisterAiService(modelName="llm_interactive_model", retrievalAugmentor=RagAugmentor.class)
public interface AugmentedOllamaService {


    @SystemMessage("""
    You are an experienced network engineer tasked with responding queries related to RFC documents.
    You know a lot about RFCs but if you do not know the answer to a question, please respond that you do not know.
    Prioritize responses in the same language as the request.
    """)

    String chat(@MemoryId Object session, @UserMessage String userMsg);
    String chat(@UserMessage String userMsg);
}
