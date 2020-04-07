package ru.privetdruk.wessengerrest.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements Serializable {
    @Id
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.Text.class)
    private String name;
    @JsonView(Views.IdText.class)
    private String picture;
    private String email;
    @JsonView(Views.FullProfile.class)
    private String gender;
    @JsonView(Views.FullProfile.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "subscriber", orphanRemoval = true)
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserSubscription> subscribers = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "'}";
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Set<UserSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<UserSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<UserSubscription> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<UserSubscription> subscribers) {
        this.subscribers = subscribers;
    }
}
