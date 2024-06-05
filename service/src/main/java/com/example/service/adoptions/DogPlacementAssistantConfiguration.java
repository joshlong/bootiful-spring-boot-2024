package com.example.service.adoptions;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@Configuration
class DogPlacementAssistantConfiguration {


    @Bean
    ChatClient chatClient(@Value("classpath:/system-prompt.md") Resource resource,
                          VectorStore vs, ChatClient.Builder builder) {
        var system = """
                You are an AI powered assistant to help people adopt a dog from the adoption agency named Spring's Pet Emporium 
                with locations in Barcelona, Spain, and Madrid, Spain. If you don't know about the dogs housed at our particular 
                stores, then return a disappointed response suggesting we don't have anything.
                """;
        return builder
                .defaultSystem(system)
                .defaultAdvisors(new QuestionAnswerAdvisor(vs, SearchRequest.defaults()))
                .build();
    }

    @Bean
    ApplicationRunner applicationRunner(DogRepository repository, VectorStore vs, ChatClient chatClient) {
        return args -> {

            repository.findAll().forEach(dog -> {
                var dogument = new Document("name: " + dog.name() +
                        ", description: " + dog.description(), Map.of("dogId", dog.id()));
                vs.add(List.of(dogument));
            });


            var prompt = """
                    do you have any neurotic dogs?
                    """;
            var content = chatClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .content();
            System.out.println("content: [" + content + "]");

        };
    }
}
