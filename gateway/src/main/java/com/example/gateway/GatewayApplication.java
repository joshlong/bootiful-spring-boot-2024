package com.example.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.security.config.Customizer.withDefaults;


@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    private static final String WILDCARD = "**";

    private static final String CRM_SERVICE_PREFIX = "/api/";
    private static final String CRM_SERVICE_HOST = "http://localhost:8080";

    private static final String UI_PREFIX = "/";
    private static final String UI_HOST = "http://localhost:9000";

    // TODO: do _NOT_ reorder these! the API MUST come first! can we use @Ordered?
    @Bean
    RouterFunction<ServerResponse> apiRouteGets() {
        return route("crmGets")
                .GET(CRM_SERVICE_PREFIX + WILDCARD, http(CRM_SERVICE_HOST))
                .before(rewritePath(CRM_SERVICE_PREFIX + "(?<segment>.*)", "/${segment}"))
                .filter(tokenRelay())
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> apiRoutePosts() {
        return route("crmPosts")
                .POST(CRM_SERVICE_PREFIX + WILDCARD, http(CRM_SERVICE_HOST))
                .before(rewritePath(CRM_SERVICE_PREFIX + "(?<segment>.*)", "/${segment}"))
                .filter(tokenRelay())
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> uiRoute() {
        return route("ui")
                .GET(UI_PREFIX + WILDCARD, http(UI_HOST))
                .before(rewritePath(UI_PREFIX + "(?<segment>.*)", "/${segment}"))
                .build();
    }

    @Bean
    SecurityFilterChain mySecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .build();
    }

}