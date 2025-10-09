package org.example.marketing.config

import org.springframework.ai.document.MetadataMode
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.openai.OpenAiEmbeddingModel
import org.springframework.ai.openai.OpenAiEmbeddingOptions
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.ai.retry.RetryUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmbeddingConfig {

    @Bean
    fun openAiApi(@Value("\${spring.ai.openai.api-key}") key: String): OpenAiApi =
        OpenAiApi.builder()
            .apiKey(key)
            .build()

    @Bean
    fun customEmbeddingModel(api: OpenAiApi): EmbeddingModel =
        OpenAiEmbeddingModel(
            api,
            MetadataMode.EMBED,                       // 문장 전체 벡터
            OpenAiEmbeddingOptions.builder()
                .model("text-embedding-3-small")
                .user("marketing-service")
                .build(),
            RetryUtils.DEFAULT_RETRY_TEMPLATE         // 네트워크 재시도
        )

}