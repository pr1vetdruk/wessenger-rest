package ru.privetdruk.wessengerrest.service;

import ru.privetdruk.wessengerrest.domain.User;

public interface ProfileService {
    User changeSubscription(User channel, User subscriber);
}
