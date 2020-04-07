package ru.privetdruk.wessengerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.UserSubscription;
import ru.privetdruk.wessengerrest.domain.UserSubscriptionId;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
