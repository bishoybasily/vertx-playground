package com.gmail.bishoybasily.demo_vertx.model.repository;

import com.gmail.bishoybasily.demo_vertx.HelperSQL;
import com.gmail.bishoybasily.demo_vertx.model.entity.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.vertx.reactivex.sqlclient.Row;

import javax.inject.Inject;
import java.util.UUID;

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

    public Single<User> read(String id) {
        return helperSQL.execute("select id, name from users where id=?", userMapper(), id).singleOrError();
    }

    public Observable<User> read() {
        return helperSQL.execute("select id, name from users", userMapper());
    }

    public Single<User> create(User user) {

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
