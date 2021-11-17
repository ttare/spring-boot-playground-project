package com.example.imagesharingapi.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "images")
public class Image extends Base {
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Album album;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "images_tags")
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "images_watchers")
    private Set<User> watchers = new HashSet<>();

    @OneToMany(mappedBy = "image")
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<User> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<User> watchers) {
        this.watchers = watchers;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
