package ru.privetdruk.wessengerrest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.service.MessageService;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class IndexController {
    private final MessageService messageService;
    private final ObjectWriter writer;

    @Value("${spring.profiles.active}")
    private String profile;

    public IndexController(MessageService messageService, ObjectMapper mapper) {
        this.messageService = messageService;
        this.writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);

            String messages = writer.writeValueAsString(messageService.findAll());
            model.addAttribute("messages", messages);
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
