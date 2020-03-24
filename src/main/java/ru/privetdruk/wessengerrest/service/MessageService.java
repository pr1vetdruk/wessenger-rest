package ru.privetdruk.wessengerrest.service;

import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.Message;

import java.util.List;

@Service
public interface MessageService {
    List<Message> findAll();
    Message save(Message message);
    Message update(Message newMessage, Message oldMessage);
    void delete(Message message);
}
