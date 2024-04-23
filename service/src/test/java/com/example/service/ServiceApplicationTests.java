package com.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class ServiceApplicationTests {

	@Test
	void contextLoads() {
		ApplicationModules.of(ServiceApplication.class).verify();
	}

}
