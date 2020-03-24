package ru.privetdruk.wessengerrest.service;

import org.springframework.beans.BeanUtils;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message save(Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public Message update(Message newMessage, Message oldMessage) {
        BeanUtils.copyProperties(newMessage, oldMessage, "id");
        return messageRepository.save(oldMessage);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
