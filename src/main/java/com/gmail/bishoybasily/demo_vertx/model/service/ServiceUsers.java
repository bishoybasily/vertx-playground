package com.gmail.bishoybasily.demo_vertx.model.service;

import com.gmail.bishoybasily.demo_vertx.model.entity.User;
import com.gmail.bishoybasily.demo_vertx.model.repository.RepositoryUsers;
import io.reactivex.Observable;

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

    public Observable<User> read() {
        return repositoryUsers.read();
    }

}
