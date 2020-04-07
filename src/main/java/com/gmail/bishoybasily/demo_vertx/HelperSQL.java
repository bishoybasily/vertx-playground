package com.gmail.bishoybasily.demo_vertx;


import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.vertx.reactivex.sqlclient.Pool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.SqlResult;
import io.vertx.reactivex.sqlclient.Tuple;
import lombok.RequiredArgsConstructor;


/**
 * @author bishoybasily
 * @since 2020-04-05
 */
@RequiredArgsConstructor
public class HelperSQL {

    private final Pool pool;

    public Single<Integer> execute(String sql, Object... args) {
        return pool.rxGetConnection()
                .flatMap(conn -> conn.preparedQuery(sql).rxExecute(Tuple.wrap(args)))
                .map(SqlResult::rowCount);
    }

    public <T> Observable<T> execute(String sql, Function<Row, T> mapper) {
        return pool.rxGetConnection()
                .flatMap(conn -> conn.query(sql).rxExecute())
                .flatMapObservable(Observable::fromIterable)
                .map(mapper);
    }

    public <T> Observable<T> execute(String sql, Function<Row, T> mapper, Object... args) {
        return pool.rxGetConnection()
                .flatMap(conn -> conn.preparedQuery(sql).rxExecute(Tuple.wrap(args)))
                .flatMapObservable(Observable::fromIterable)
                .map(mapper);
    }

}
