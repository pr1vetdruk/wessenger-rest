package ru.privetdruk.wessengerrest.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.dto.EventType;
import ru.privetdruk.wessengerrest.dto.ObjectType;
import ru.privetdruk.wessengerrest.repository.MessageRepository;
import ru.privetdruk.wessengerrest.util.WebSocketSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final BiConsumer<EventType, Message> webSocketSender;

    public MessageServiceImpl(MessageRepository messageRepository, WebSocketSender webSocketSender) {
        this.messageRepository = messageRepository;
        this.webSocketSender = webSocketSender.getSender(ObjectType.MESSAGE, Views.Message.IdText.class);
    }


    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message save(Message message) {
        message.setCreationDate(LocalDateTime.now());
        Message updatedMessage = messageRepository.save(message);
        webSocketSender.accept(EventType.CREATE, updatedMessage);
        return updatedMessage;
    }

    @Override
    public Message update(Message newMessage, Message oldMessage) {
        BeanUtils.copyProperties(newMessage, oldMessage, "id");
        Message updatedMessage = messageRepository.save(oldMessage);
        webSocketSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
        webSocketSender.accept(EventType.REMOVE, message);
    }
}
