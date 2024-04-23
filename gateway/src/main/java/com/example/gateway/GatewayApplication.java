package com.example.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Controller
@ResponseBody
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


}

@ResponseBody
@Controller
class SimpleController {


    static final String WILDCARD = "**";

    static final String API_PREFIX = "/docs/";

    static final String UI_PREFIX = "/";


    @GetMapping(UI_PREFIX + WILDCARD)
    ResponseEntity<?> ui(ProxyExchange<byte[]> proxy) {
        var path = proxy.path(API_PREFIX);
        return proxy
                .uri(URI.create("https://cnn.com/" + path))
                .get();
    }

    @GetMapping(API_PREFIX + WILDCARD)
    ResponseEntity<?> api (ProxyExchange<byte[]> proxy) {
        var path = proxy.path(API_PREFIX);
        return proxy
                .uri(URI.create("https://docs.spring.io/" + path))
                .get();
    }


}