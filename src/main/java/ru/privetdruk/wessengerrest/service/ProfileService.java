package ru.privetdruk.wessengerrest.service;

import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.UserSubscription;

import java.util.List;

public interface ProfileService {
    User changeSubscription(User channel, User subscriber);

    User findUserById(String id);

    List<UserSubscription> getSubscribers(User channel);

    UserSubscription changeSubscriptionStatus(User channel, User subscriber);
}
