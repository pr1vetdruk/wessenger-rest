package ru.privetdruk.wessengerrest.service;

import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.Comment;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        return commentRepository.save(comment);
    }
}
