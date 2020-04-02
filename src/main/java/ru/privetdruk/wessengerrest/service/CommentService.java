package ru.privetdruk.wessengerrest.service;

import ru.privetdruk.wessengerrest.domain.Comment;
import ru.privetdruk.wessengerrest.domain.User;

public interface CommentService {
    public Comment create(Comment comment, User user);
}
