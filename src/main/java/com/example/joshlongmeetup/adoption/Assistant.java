package com.example.joshlongmeetup.adoption;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("ai")
@Configuration
public class Assistant {

    @Bean
    ApplicationRunner printOpenAiApiKey(@Value("${spring.ai.openai.api-key}") String apiKey){
        return args -> System.out.println("apiKey = " + apiKey);
    }


    @Bean
    ApplicationRunner runner(ChatClient chatClient){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                var content = chatClient
                        .prompt()
                        .user("do you have any neurotic dogs?")
                        .call()
                        .entity(DogAdoptionSuggestion.class);
                System.out.println("reply [ " + content + " ]");
            }
        };
    }

    // promt stuffing
    // RETRIEVAL AUGMENTED GENERATION (RAG)
    // in AI engineering
    @Bean
    ChatClient chatClient(ChatClient.Builder builder, DogRepository repository, VectorStore vectorStore, @Value("${spring.ai.vectorstore.pgvector.initialize-schema}") Boolean vectorFlag){
        if(vectorFlag){
            repository.findAll().forEach(dog -> {
                var document = new Document("id : %s, name: %s, description: %s".formatted(dog.id(), dog.name(), dog.description()));
                vectorStore.add(List.of(document));
            });
        }

        var system = """
				You are an AI powered assistant to help people adopt a dog from the adoption agency named Pooch Palace
				with locations in Seoul, Las Vegas, Tokyo, Krakow, Singapore, Paris, London and San Francisco. If you don't know about the dogs housed at our particular
				stores, then return a disappointed response suggesting we don't have anything.
				""";
        return builder//
                .defaultSystem(system)//
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))//
                .build();
    }
}

record DogAdoptionSuggestion(String name, String description, Integer id) {}