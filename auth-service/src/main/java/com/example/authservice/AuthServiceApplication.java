package com.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
/*
	@Bean
	PasswordEncoder passwordEncoder (){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder() ;
	}*/
/*
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
	UserDetailsManager users(PasswordEncoder encoder) {
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
	}*/
}
