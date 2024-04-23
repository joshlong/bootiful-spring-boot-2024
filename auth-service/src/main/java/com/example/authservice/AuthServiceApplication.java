package com.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Set;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder (){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder() ;
	}

	@Bean
	RegisteredClientRepository clients() {
		var rc = RegisteredClient
				.withId("spring")
				.scopes(s -> s.addAll(Set.of("openid", "user.read")))
				.clientSecret("crm")
				.clientId("crm")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.build();
		return new InMemoryRegisteredClientRepository(rc );
	}

	@Bean
	InMemoryUserDetailsManager users(PasswordEncoder encoder) {
		return new InMemoryUserDetailsManager(
				User.withUsername("jlong")
						.password(encoder.encode("pw"))
						.roles("USER")
						.build()
		);
	}
}
