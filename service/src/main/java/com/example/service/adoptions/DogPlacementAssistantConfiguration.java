package com.example.service.adoptions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
@RegisterReflectionForBinding(DogAdoptionSuggestion.class)
class DogPlacementAssistantConfiguration {

	@Bean
	ChatClient chatClient(VectorStore vs, ChatClient.Builder builder) {
		var system = """
				You are an AI powered assistant to help people adopt a dog from the adoption agency named Spring's Pet Emporium
				with locations in Barcelona, Spain, and Madrid, Spain. If you don't know about the dogs housed at our particular
				stores, then return a disappointed response suggesting we don't have anything.
				""";
		return builder//
			.defaultSystem(system)//
			.defaultAdvisors(new QuestionAnswerAdvisor(vs, SearchRequest.defaults()))//
			.build();
	}

	@Bean
	JdbcClient jdbcClient(DataSource dataSource) {
		return JdbcClient.create(dataSource);
	}

	@Bean
	ApplicationRunner applicationRunner(JdbcClient db, DogRepository repository, VectorStore vs,
			ChatClient chatClient) {
		return args -> {

			db.sql("delete from vector_store").update();

			repository.findAll().forEach(dog -> {
				var dogument = new Document("name: " + dog.name() + ", description: " + dog.description(),
						Map.of("dogId", dog.id()));
				vs.add(List.of(dogument));
			});

			var prompt = """
					do you have any neurotic dogs?
					""";
			var content = chatClient.prompt().user(prompt).call().entity(DogAdoptionSuggestion.class);
			System.out.println(content);

		};
	}

}

record DogAdoptionSuggestion(String name, String description) {
}