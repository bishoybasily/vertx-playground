package com.gmail.bishoybasily.playground.vertx;


import io.vertx.sqlclient.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @author bishoybasily
 * @since 2020-04-05
 */
@RequiredArgsConstructor
public class HelperSQL {

    private final Pool pool;

    public Mono<Integer> execute(String sql, Object... args) {

        return Mono.create(new Consumer<MonoSink<Integer>>() {
            @Override
            public void accept(MonoSink<Integer> sink) {

                pool.getConnection(sqlConnectionResult -> {

                    if (sqlConnectionResult.succeeded()) {

                        SqlConnection sqlConnection = sqlConnectionResult.result();

                        sink.onDispose(sqlConnection::close);
                        sink.onCancel(sqlConnection::close);

                        sqlConnection.preparedQuery(sql).execute(Tuple.wrap(args), rowSetResult -> {

                            if (rowSetResult.succeeded()) {

                                RowSet<Row> rowRowSet = rowSetResult.result();

                                sink.success(rowRowSet.rowCount());

                            } else {
                                sink.error(rowSetResult.cause());
                            }

                        });

                    } else {
                        sink.error(sqlConnectionResult.cause());
                    }

                });

            }
        });


//        return pool.rxGetConnection()
//                .flatMap(conn -> conn.preparedQuery(sql).rxExecute(Tuple.wrap(args)).doFinally(conn::close))
//                .map(SqlResult::rowCount);
    }

    public <T> Flux<T> execute(String sql, Function<Row, T> mapper, Object... args) {

        return Flux.create(new Consumer<FluxSink<Row>>() {

            @Override
            public void accept(FluxSink<Row> sink) {

                pool.getConnection(sqlConnectionResult -> {

                    if (sqlConnectionResult.succeeded()) {

                        SqlConnection sqlConnection = sqlConnectionResult.result();

                        sink.onDispose(sqlConnection::close);
                        sink.onCancel(sqlConnection::close);

                        sqlConnection.preparedQuery(sql).execute(Tuple.wrap(args), rowSetResult -> {

                            if (rowSetResult.succeeded()) {

                                RowSet<Row> rowRowSet = rowSetResult.result();

                                rowRowSet.forEach(sink::next);

                                sink.complete();

                            } else {
                                sink.error(rowSetResult.cause());
                            }

                        });

                    } else {
                        sink.error(sqlConnectionResult.cause());
                    }

                });

            }
        }).map(mapper);

//        return pool.rxGetConnection()
//                .flatMap(conn -> conn.preparedQuery(sql).rxExecute(Tuple.wrap(args)).doFinally(conn::close))
//                .flatMapObservable(Observable::fromIterable)
//                .map(mapper);
    }

}
