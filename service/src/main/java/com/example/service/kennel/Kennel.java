package com.example.service.kennel;

import com.example.service.adoptions.DogAdoptedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
class Kennel {

	@ApplicationModuleListener
	void onDogAdoptedEvent(DogAdoptedEvent dogAdoptedEvent) throws Exception {
		System.out.println("starting handling for [" + dogAdoptedEvent + "]");
		Thread.sleep(10_000);
		System.out.println(
				"onDogAdoptedEvent [" + dogAdoptedEvent + "]! " + "Hurray! guess we'd better prepare the paperwork...");
		Thread.sleep(10_000);
		System.out.println("all done [" + dogAdoptedEvent + "]!");
	}

}
