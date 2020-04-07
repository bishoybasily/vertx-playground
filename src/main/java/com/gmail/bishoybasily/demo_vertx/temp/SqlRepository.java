package com.gmail.bishoybasily.demo_vertx.temp;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.sqlclient.*;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * @author bishoybasily
 * @since 2020-04-05
 */
public interface SqlRepository<T> {

    default Single<T> one(String id) {
        return select(getOneSql(id)).singleOrError();
    }

    default Observable<T> all() {
        return select(getAllSql());
    }

    private Observable<T> select(String sql) {

        return Observable.create(emitter -> {

            getPool().getConnection(sqlConnectionAsyncResult -> {

                if (sqlConnectionAsyncResult.succeeded()) {

                    SqlConnection sqlConnection = sqlConnectionAsyncResult.result();

                    sqlConnection.prepare("", new Handler<AsyncResult<PreparedStatement>>() {
                        @Override
                        public void handle(AsyncResult<PreparedStatement> event) {
                            PreparedStatement result = event.result();
                            result.cursor(Tuple.of("", "", ""));
                        }
                    });

                    sqlConnection.query(sql).mapping(getMapper()).execute(rowSetAsyncResult -> {

                        if (rowSetAsyncResult.succeeded()) {

                            rowSetAsyncResult.result().forEach(emitter::onNext);

                            emitter.onComplete();

                        } else {
                            emitter.onError(rowSetAsyncResult.cause());
                        }
                    });

                } else {
                    emitter.onError(sqlConnectionAsyncResult.cause());
                }

            });

        });
    }

    default Function<Row, T> getMapper() {
        return new Function<Row, T>() {
            @Override
            public T apply(Row row) {
                return null;
            }
        };
    }

    default String getOneSql(String id) {

        String select = StringUtils.prefixedJoin("select ", ",", "id", "name");
        String where = StringUtils.prefixedJoin("where ", "");


        String query = "select id, name from users where id = id";


        return query;
    }

    default String getAllSql() {
        return "select id, name from users";
    }

    default Map<String, Object> oneFilter() {
        return Collections.singletonMap("id = ?", 1);
    }

    default Map<String, Object> allFilter() {
        return Collections.emptyMap();
    }

    Pool getPool();


}
