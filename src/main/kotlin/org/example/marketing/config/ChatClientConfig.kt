package org.example.marketing.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig {
    @Bean
    fun chatClient(model: ChatModel): ChatClient =
        ChatClient.builder(model)      // static builder
            .build()
}