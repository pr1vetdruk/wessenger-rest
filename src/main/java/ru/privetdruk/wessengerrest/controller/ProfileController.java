package ru.privetdruk.wessengerrest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.privetdruk.wessengerrest.domain.User;
import ru.privetdruk.wessengerrest.domain.UserSubscription;
import ru.privetdruk.wessengerrest.domain.Views;
import ru.privetdruk.wessengerrest.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(@AuthenticationPrincipal User subscriber, @PathVariable("channelId") User channel) {
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdText.class)
    public List<UserSubscription> subscribers(@PathVariable("channelId") User channel) {
        return profileService.getSubscribers(channel);
    }

    @PostMapping("get-subscribers/{subscriberId}")
    @JsonView(Views.IdText.class)
    public UserSubscription changeSubscriptionStatus(@AuthenticationPrincipal User channel, @PathVariable("subscriberId") User subscriber) {
        return profileService.changeSubscriptionStatus(channel, subscriber);
    }
}
