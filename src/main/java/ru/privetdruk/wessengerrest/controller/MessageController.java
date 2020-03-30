package ru.privetdruk.wessengerrest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.Message.IdText.class)
    public List<Message> messagesList() {
        return messageService.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageService.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message oldMessage, @RequestBody Message newMessage) {
        return messageService.update(newMessage, oldMessage);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }

    /*@MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message change(Message message) {
        return messageService.save(message);
    }*/
}
