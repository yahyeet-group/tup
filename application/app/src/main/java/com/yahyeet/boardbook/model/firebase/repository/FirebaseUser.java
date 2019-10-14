package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FirebaseUser {
    private String id;
    private String name;
    private List<String> friends;

    public FirebaseUser() {}

    public FirebaseUser(String id, String name) {
        this.id = id;
        this.name = name;
        friends = new ArrayList<>();
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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();

        if (name != null) {
            data.put("name", name);
        }

        if (friends != null) {
            data.put("friends", friends);
        }

        return data;
    }

    public static FirebaseUser fromUser(User user) {
        FirebaseUser firebaseUser = new FirebaseUser(user.getId(), user.getName());

        if (user.getFriends() != null) {
            firebaseUser.setFriends(
                    user
                            .getFriends()
                            .stream()
                            .map(AbstractEntity::getId)
                            .collect(Collectors.toList())
            );
        }

        return firebaseUser;
    }

    public User toUser() {
        User user = new User(name);
        user.setId(getId());
        return user;
    }
}
