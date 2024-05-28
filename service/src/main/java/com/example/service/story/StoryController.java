package com.example.service.story;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
class StoryController {

    private final ChatClient singularity  ;

    StoryController(ChatClient singularity) {
        this.singularity = singularity;
    }

    @GetMapping("/story")
    Map<String, String> story() {
        var prompt = """
                 Dear Singularity,
                 
                 Tell me a story about the amazing Java developers
                 in Sophia, Bulgaria, and do so in the style of famed 
                 children's author, Dr. Seuss.
                 
                 Cordially,
                 Josh
                """;
        return Map.of("story", this.singularity.prompt().user(prompt).call().content());
    }
}

