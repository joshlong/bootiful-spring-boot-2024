package com.example.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
class GatewayProxyController {

    private static final String WILDCARD = "**";

    private static final String API_PREFIX = "/api/";

    private static final String UI_PREFIX = "/ui/";

    @GetMapping(API_PREFIX + WILDCARD)
    ResponseEntity<?> api(ProxyExchange<byte[]> proxy) {
        var path = proxy.path(API_PREFIX);
        return proxy
                .uri(URI.create("http://localhost:8080/" + path))
                .get();
    }

    @RequestMapping(UI_PREFIX + WILDCARD)
    ResponseEntity<?> ui(ProxyExchange<byte[]> proxy) {
        var path = proxy.path(UI_PREFIX);
        var uri = URI.create("http://localhost:9000/" + path);
        System.out.println("going to forward to [" + uri + "]");
        return proxy
                .uri(uri)
                .get();
    }


}