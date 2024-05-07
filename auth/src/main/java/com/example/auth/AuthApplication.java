package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Set;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) {
        var user = User//
                .withUsername("jlong")//
                .password(passwordEncoder.encode("pw"))//
                .roles("user", "admin")//
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    RegisteredClientRepository registeredClients(PasswordEncoder passwordEncoder) {
        var clientId = "bootiful";
        var clientSecret = "bootiful";
        var rc = RegisteredClient//
                .withId(clientId)
                .clientId(clientId)
                .authorizationGrantTypes(c -> c
                        .addAll(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE, AuthorizationGrantType.REFRESH_TOKEN)))//
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSecret(passwordEncoder.encode(clientSecret))
                .redirectUri("http://127.0.0.1:1010/login/oauth2/code/spring")
                .scopes(c -> c.addAll(Set.of("user.read", "user.write", "openid")))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        return new InMemoryRegisteredClientRepository(rc);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return createDelegatingPasswordEncoder();
    }


}
