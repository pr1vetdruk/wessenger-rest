package ru.privetdruk.wessengerrest.domain;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserSubscriptionId implements Serializable {
    @JsonView(Views.Id.class)
    private String channelId;
    @JsonView(Views.Id.class)
    private String subscriberId;

    public UserSubscriptionId() {
    }

    public UserSubscriptionId(String channelId, String subscriberId) {
        this.channelId = channelId;
        this.subscriberId = subscriberId;
    }
}
