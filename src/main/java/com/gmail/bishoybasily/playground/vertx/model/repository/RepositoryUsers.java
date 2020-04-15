package com.gmail.bishoybasily.playground.vertx.model.repository;

import com.gmail.bishoybasily.playground.vertx.HelperSQL;
import com.gmail.bishoybasily.playground.vertx.model.entity.User;
import io.vertx.sqlclient.Row;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
public class RepositoryUsers {

    private final HelperSQL helperSQL;

    @Inject
    public RepositoryUsers(HelperSQL helperSQL) {
        this.helperSQL = helperSQL;
    }

    public Mono<User> read(String id) {
        return helperSQL.execute("select id, name from users where id=?", userMapper(), id).single();
    }

    public Flux<User> read() {
        return helperSQL.execute("select id, name from users", userMapper());
    }

    public Mono<User> create(User user) {

        user.setId(UUID.randomUUID().toString());

        return helperSQL.execute("insert into users (id, name) values (?, ?)", user.getId(), user.getName())
                .map(rows -> user);
    }

    private Function<Row, User> userMapper() {
        return row -> {
            return new User().setId(row.getString(0)).setName(row.getString(1));
        };
    }

}
