package ru.privetdruk.wessengerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.privetdruk.wessengerrest.domain.User;

public interface UserDetailsRepository extends JpaRepository<User, String> {
}
