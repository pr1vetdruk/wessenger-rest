package ru.privetdruk.wessengerrest.service;

import ru.privetdruk.wessengerrest.domain.Message;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    List<Message> findAll();
    Message save(Message message) throws IOException;
    Message update(Message newMessage, Message oldMessage) throws IOException;
    void delete(Message message);
}
