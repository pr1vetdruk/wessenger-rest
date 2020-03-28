package ru.privetdruk.wessengerrest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.service.MessageService;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class IndexController {
    private final MessageService messageService;

    @Value("${spring.profiles.active}")
    private String profile;

    public IndexController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
            data.put("messages", messageService.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
