package ru.privetdruk.wessengerrest.service;

import org.springframework.stereotype.Service;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.UserSubscription;
import ru.privetdruk.wessengerrest.repository.UserDetailsRepository;
import ru.privetdruk.wessengerrest.repository.UserSubscriptionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserDetailsRepository userDetailsRepository;
    private final UserSubscriptionRepository subscriptionRepository;

    public ProfileServiceImpl(UserDetailsRepository userDetailsRepository, UserSubscriptionRepository subscriptionRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.subscriptionRepository = subscriptionRepository;
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

    @Override
    public List<UserSubscription> getSubscribers(User channel) {
        return subscriptionRepository.findByChannel(channel);
    }

    @Override
    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = subscriptionRepository.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return subscriptionRepository.save(subscription);
    }
}
