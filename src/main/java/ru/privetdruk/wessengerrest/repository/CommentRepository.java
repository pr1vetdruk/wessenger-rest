package ru.privetdruk.wessengerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.privetdruk.wessengerrest.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
