package com.example.service.ai;

import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
class ChatController {

    private final ChatClient singularity;

    ChatController(ChatClient singularity) {
        this.singularity = singularity;
    }


    @GetMapping("/story")
    Map<String, String> story() {
        var request = """
                Carissima Singolarità, 
                                
                Per favore, scrivi una storia sugli straordinari developers Java e Spring Boot
                nella bellissima città di Roma. 
                 
                 E, per favore, fallo nello stilo del famoso autore di libri per bambini 
                 Dr. Seuss. 
                 
                 Grazie e Cordialmente,
                 Josh
                """;

        var response = this.singularity.call(request);

        return Map.of("story", response);
    }


}
