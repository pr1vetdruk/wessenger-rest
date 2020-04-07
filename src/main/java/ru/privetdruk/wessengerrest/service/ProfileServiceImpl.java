package ru.privetdruk.wessengerrest.service;

import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.UserSubscription;
import ru.privetdruk.wessengerrest.repository.UserDetailsRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserDetailsRepository userDetailsRepository;

    public ProfileServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        return userDetailsRepository.save(channel);
    }

    @Override
    public User findUserById(String id) {
        return userDetailsRepository.findById(id).orElse(null);
    }
}
