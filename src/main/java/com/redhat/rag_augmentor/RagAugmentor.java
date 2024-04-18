package com.redhat.rag_augmentor;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

import io.quarkiverse.langchain4j.chroma.ChromaEmbeddingStore;

import java.util.function.Supplier;
import jakarta.inject.Singleton;

@Singleton // required for websocket operation
public class RagAugmentor implements Supplier<RetrievalAugmentor> {

    private EmbeddingStoreContentRetriever retriever;

    RagAugmentor(ChromaEmbeddingStore store, EmbeddingModel model) {
        this.retriever = EmbeddingStoreContentRetriever.builder()
                  .embeddingModel(model)
                  .embeddingStore(store)
                  .maxResults(40)
                  .build();
    }

    @Override
    public RetrievalAugmentor get() {
        return DefaultRetrievalAugmentor.builder()
          .contentRetriever(this.retriever).build();
    }
}
