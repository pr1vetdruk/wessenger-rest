package ru.privetdruk.wessengerrest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.privetdruk.wessengerrest.domain.Comment;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.Message.Full.class)
    public Comment create(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        return commentService.create(comment, user);
    }
}
