package ru.privetdruk.wessengerrest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.dto.MessagePageDto;
import ru.privetdruk.wessengerrest.service.MessageService;

import java.io.IOException;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto messagesList(
            @PageableDefault(size = MessageService.MESSAGES_PER_PAGE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {
        return messageService.findForUser(user, pageable);
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message, @AuthenticationPrincipal User user) throws IOException {
        return messageService.create(message, user);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message oldMessage, @RequestBody Message newMessage) throws IOException {
        return messageService.update(newMessage, oldMessage);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }


}
