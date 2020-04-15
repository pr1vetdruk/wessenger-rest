package ru.privetdruk.wessengerrest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.dto.MessagePageDto;
import ru.privetdruk.wessengerrest.service.MessageService;
import ru.privetdruk.wessengerrest.service.ProfileService;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class IndexController {
    private final MessageService messageService;
    private final ProfileService profileService;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    @Value("${spring.profiles.active:prod}")
    private String profile;

    public IndexController(MessageService messageService, ObjectMapper mapper, ProfileService profileService) {
        this.messageService = messageService;
        this.profileService = profileService;

        ObjectMapper config = mapper.setConfig(mapper.getSerializationConfig());
        this.messageWriter = config.writerWithView(Views.FullMessage.class);
        this.profileWriter = config.writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            User userFromDb = profileService.findUserById(user.getId());
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, MessageService.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findForUser(user, pageRequest);

            String serializedMessages = messageWriter.writeValueAsString(messagePageDto.getMessages());

            model.addAttribute("messages", serializedMessages);
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());
        } else {
            model.addAttribute("messages", null);
            model.addAttribute("profile", null);
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
