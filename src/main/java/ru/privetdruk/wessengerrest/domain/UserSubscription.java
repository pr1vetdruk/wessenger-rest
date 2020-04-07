package ru.privetdruk.wessengerrest.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class UserSubscription {
    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id;

    @MapsId("channelId")
    @ManyToOne
    @JsonView(Views.IdText.class)
    @JsonIdentityReference
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    private User channel;

    @MapsId("subscriberId")
    @ManyToOne
    @JsonView(Views.IdText.class)
    @JsonIdentityReference
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    private User subscriber;

    @JsonView(Views.IdText.class)
    private boolean active;

    public UserSubscription(User channel, User subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(channel.getId(), subscriber.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscription that = (UserSubscription) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserSubscription{id=" + id + '}';
    }

    public UserSubscriptionId getId() {
        return id;
    }

    public void setId(UserSubscriptionId id) {
        this.id = id;
    }

    public User getChannel() {
        return channel;
    }

    public void setChannel(User channel) {
        this.channel = channel;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
