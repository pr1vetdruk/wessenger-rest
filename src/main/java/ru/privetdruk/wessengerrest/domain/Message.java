package ru.privetdruk.wessengerrest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString(of = {"id", "text"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Message.Id.class)
    private Long id;
    @JsonView(Views.Message.Text.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonView(Views.Message.Full.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.Message.Full.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(Views.Message.Full.class)
    private List<Comment> comments;

    @JsonView(Views.Message.Full.class)
    private String link;
    @JsonView(Views.Message.Full.class)
    private String linkTitle;
    @JsonView(Views.Message.Full.class)
    private String linkDescription;
    @JsonView(Views.Message.Full.class)
    private String linkCover;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getLinkCover() {
        return linkCover;
    }

    public void setLinkCover(String linkCover) {
        this.linkCover = linkCover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id != null ? id.equals(message.id) : message.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
