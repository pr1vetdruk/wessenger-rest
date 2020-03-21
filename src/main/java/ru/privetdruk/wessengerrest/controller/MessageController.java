package ru.privetdruk.wessengerrest.controller;

import org.springframework.web.bind.annotation.*;
import ru.privetdruk.wessengerrest.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "First test message");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "Second test message");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "Third test message");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> messagesList() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(messages.size() + 1));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> sourceMessage = getMessage(id);

        message.put("id", id);
        sourceMessage.putAll(message);

        return sourceMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
