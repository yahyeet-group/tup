package com.yahyeet.boardbook.model.service;

import com.yahyeet.boardbook.model.entity.User;

import java.util.concurrent.CompletableFuture;

public interface IAuthService {
    CompletableFuture<String> login(String email, String password);

    void logout();

    CompletableFuture<User> signup(String email, String password, String name) throws Exception;
}
