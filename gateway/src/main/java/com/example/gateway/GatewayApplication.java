package com.example.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Controller
@ResponseBody
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    private static final String WILDCARD = "**";

    private static final String API_PREFIX = "/api/";

    private static final String UI_PREFIX = "/ui/";

    @Bean
    RouterFunction<ServerResponse> apiRoute () {
        return route("crm")
//                .GET(API_PREFIX , http("http://localhost:8080/"))
                .GET(API_PREFIX + WILDCARD, http("http://localhost:8080"))
                .filter(tokenRelay())
                .before(rewritePath(API_PREFIX + "(?<segment>.*)", "/${segment}"))
                .build();
    }

    @RequestMapping(UI_PREFIX + WILDCARD)
    ResponseEntity<?> uiRoute(ProxyExchange<byte[]> proxy) {
        var path = proxy.path(UI_PREFIX);
        return proxy
                .uri(URI.create("http://localhost:9000/" + path))
                .get();
    }


}