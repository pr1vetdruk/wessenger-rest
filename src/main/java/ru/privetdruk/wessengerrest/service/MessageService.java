package ru.privetdruk.wessengerrest.service;

import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.User;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    List<Message> findAll();
    Message create(Message message, User user) throws IOException;
    Message update(Message newMessage, Message oldMessage) throws IOException;
    void delete(Message message);
}
