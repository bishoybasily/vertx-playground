package com.gmail.bishoybasily.playground.vertx.model.service;

import com.gmail.bishoybasily.playground.vertx.model.entity.User;
import com.gmail.bishoybasily.playground.vertx.model.repository.RepositoryUsers;
import reactor.core.publisher.Flux;

import javax.inject.Inject;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
public class ServiceUsers {

    private final RepositoryUsers repositoryUsers;

    @Inject
    public ServiceUsers(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    public Flux<User> read() {
        return repositoryUsers.read();
    }

}
