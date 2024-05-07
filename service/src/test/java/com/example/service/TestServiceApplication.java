package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

public class TestServiceApplication {



	public static void main(String[] args) {
		SpringApplication.from(ServiceApplication::main).with(TestServiceApplication.class).run(args);
	}

}
