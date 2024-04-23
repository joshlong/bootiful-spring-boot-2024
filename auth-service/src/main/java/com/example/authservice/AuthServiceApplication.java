package com.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Set;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;

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
	RegisteredClientRepository clients(PasswordEncoder passwordEncoder) {
		var rc = RegisteredClient
				.withId("crm")
				.scopes(s -> s.add("openid"))
				.clientSecret(passwordEncoder.encode("crm"))
				.redirectUri("http://127.0.0.1:1010/login/oauth2/code/spring")
				.clientId("crm")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.authorizationGrantTypes(s -> s.addAll(Set.of(AUTHORIZATION_CODE, REFRESH_TOKEN)))
				.build();
		return new InMemoryRegisteredClientRepository(rc);
	}

	@Bean
	InMemoryUserDetailsManager users(PasswordEncoder encoder) {
		var jlong = User
				.withUsername("jlong")
				.password(encoder.encode("pw"))
				.roles("USER")
				.build();
		var ciberkleid = User
				.withUsername("ciberkleid")
				.password(encoder.encode("pw"))
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(ciberkleid, jlong);
	}
}
