package ru.privetdruk.wessengerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.privetdruk.wessengerrest.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
