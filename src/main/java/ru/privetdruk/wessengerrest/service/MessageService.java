package ru.privetdruk.wessengerrest.service;

import org.springframework.data.domain.Pageable;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.dto.MessagePageDto;

import java.io.IOException;

public interface MessageService {
    int MESSAGES_PER_PAGE = 3;

    MessagePageDto findForUser(User user, Pageable pageable);
    Message create(Message message, User user) throws IOException;
    Message update(Message newMessage, Message oldMessage) throws IOException;
    void delete(Message message);
}
