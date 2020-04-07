package com.gmail.bishoybasily.demo_vertx.repository;

import com.gmail.bishoybasily.demo_vertx.HelperSQL;
import com.gmail.bishoybasily.demo_vertx.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.vertx.reactivex.sqlclient.Row;

import javax.inject.Inject;

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

    public Single<User> one(String id) {
        return helperSQL.execute("select id, name from users where id=?", userMapper(), id).singleOrError();
    }

    public Observable<User> all() {
        return helperSQL.execute("select id, name from users", userMapper());
    }

    private Function<Row, User> userMapper() {
        return row -> {
            return new User().setId(row.getString(0)).setName(row.getString(1));
        };
    }

}
