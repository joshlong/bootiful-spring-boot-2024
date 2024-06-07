package com.example.service.adoptions;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@ResponseBody
@Transactional
@RequestMapping("/adoptions")
class AdoptionController {

	private final DogRepository repository;

	private final ApplicationEventPublisher publisher;

	AdoptionController(DogRepository repository, ApplicationEventPublisher publisher) {
		this.repository = repository;
		this.publisher = publisher;
	}

	@PostMapping("/{id}")
	void adopt(@PathVariable Integer id, @RequestBody Map<String, String> owner) {
		this.repository.findById(id).ifPresent(dog -> {
			var saved = this.repository.save(new Dog(id, dog.name(), dog.description(), dog.dob(), owner.get("name")));
			this.publisher.publishEvent(new DogAdoptedEvent(dog.id(), dog.name()));
			System.out.println("saved [" + saved + "]");
		});

	}

}
