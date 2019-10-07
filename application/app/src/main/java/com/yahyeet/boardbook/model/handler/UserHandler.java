package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class UserHandler {

    private IUserRepository userRepository;
    private IMatchRepository matchRepository;
    private List<UserHandlerListener> listeners = new ArrayList<>();

    public UserHandler(IUserRepository userRepository, IMatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    public CompletableFuture<User> create(User user) {
        // TODO: add relation entities
        return userRepository.create(user).thenCompose((u) -> {
            notifyListenersOnUserAdd(u);

            return this.populate(u);
        });
    }

    public CompletableFuture<User> find(String id) {
        return userRepository.find(id).thenCompose(this::populate);
    }


    public CompletableFuture<User> update(User user) {
        // TODO: update relation entities
        return userRepository.update(user).thenCompose((u) -> {
            notifyListenersOnUserUpdate(u);

            return this.populate(u);
        });
    }


    public CompletableFuture<Void> remove(User user) {
        // TODO: remove relationship entities?
        return userRepository.remove(user).thenApply((v) -> {
            notifyListenersOnUserRemove(user);

            return null;
        });
    }

    public CompletableFuture<List<User>> all() {
        return userRepository.all().thenCompose(users -> {
            List<CompletableFuture<User>> completableFutures = users.stream().map(this::populate).collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
            return allFutures.thenApply(future -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        });
    }

    public CompletableFuture<User> populate(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Cannot populate a user without an identifier");
        }

        return this.find(user.getId()).thenApply(u -> {
            user.setName(u.getName());

            return u;
        }).thenCompose(u -> this.userRepository.findFriendsByUserId(user.getId())).thenApply(friends -> {
            user.setFriends(friends);

            return null;
        }).thenCompose(o -> this.matchRepository.findMatchesByUserId(user.getId())).thenApply(matches -> {
            user.setMatches(matches);

            return user;
        });
    }

    public void addListener(UserHandlerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UserHandlerListener listener) {
        listeners.remove(listener);
    }

    private void notifyListenersOnUserAdd(User user) {
        for (UserHandlerListener listener : listeners) {
            listener.onAddUser(user);
        }
    }

    private void notifyListenersOnUserUpdate(User user) {
        for (UserHandlerListener listener : listeners) {
            listener.onUpdateUser(user);
        }
    }

    private void notifyListenersOnUserRemove(User user) {
        for (UserHandlerListener listener : listeners) {
            listener.onRemoveUser(user);
        }
    }
}
