package ru.privetdruk.wessengerrest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.privetdruk.wessengerrest.dto.EventType;
import ru.privetdruk.wessengerrest.dto.ObjectType;
import ru.privetdruk.wessengerrest.dto.WebSocketEventDto;

import java.util.function.BiConsumer;

@Component
public class WebSocketSender {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public WebSocketSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class<?> view) {
        ObjectWriter writer = mapper.setConfig(mapper.getSerializationConfig()).writerWithView(view);
        return (EventType eventType, T payload) -> {
            String value = null;
            try {
                value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            template.convertAndSend("/topic/activity", new WebSocketEventDto(objectType, eventType, value));
        };
    }
}
