package ru.privetdruk.wessengerrest.service;

import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.repository.UserDetailsRepository;

import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserDetailsRepository userDetailsRepository;

    public ProfileServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        return userDetailsRepository.save(channel );
    }
}
