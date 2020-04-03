package ru.privetdruk.wessengerrest.service;

import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.Comment;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.dto.EventType;
import ru.privetdruk.wessengerrest.dto.ObjectType;
import ru.privetdruk.wessengerrest.repository.CommentRepository;
import ru.privetdruk.wessengerrest.util.WebSocketSender;

import java.util.function.BiConsumer;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> webSocketSender;

    public CommentServiceImpl(CommentRepository commentRepository, WebSocketSender webSocketSender) {
        this.commentRepository = commentRepository;
        this.webSocketSender = webSocketSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepository.save(comment);

        webSocketSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}
