package com.example.imagesharingapi.models.dao;

import com.example.imagesharingapi.utils.VoteEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote extends Base {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image")
    private Image image;

    @Enumerated(EnumType.ORDINAL)
    private VoteEnum vote;

    public Vote() {
    }


    public Vote(Long imageId, Long userId) {
        this.setImage(imageId);
        this.setUser(userId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public VoteEnum getVote() {
        return vote;
    }

    public void setVote(VoteEnum vote) {
        this.vote = vote;
    }

    public void setImage(Long imageId) {
        Image image = new Image();
        image.setId(imageId);
        this.image = image;
    }

    public void setUser(Long userId) {
        User user = new User();
        user.setId(userId);
        this.user = user;
    }

}
