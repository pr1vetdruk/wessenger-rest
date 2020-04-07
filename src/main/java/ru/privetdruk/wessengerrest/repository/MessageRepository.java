package ru.privetdruk.wessengerrest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.privetdruk.wessengerrest.domain.Message;
import ru.privetdruk.wessengerrest.domain.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @EntityGraph(attributePaths = {"comments"})
    Page<Message> findByAuthorIn(List<User> users, Pageable pageable);
}
