package com.gmail.bishoybasily.demo_vertx.temp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.servicediscovery.ServiceDiscovery;
import io.vertx.reactivex.servicediscovery.types.HttpEndpoint;
import io.vertx.servicediscovery.Record;
import io.vertx.sqlclient.*;
import org.flywaydb.core.Flyway;

/**
 * @author bishoybasily
 * @since 2020-04-05
 */
public class Main {

    public static void main(String[] args) {

        Flyway.configure()
                .dataSource("", "", "")
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .load();

        ServiceDiscovery serviceDiscovery = ServiceDiscovery.create(Vertx.vertx());
        Record record = HttpEndpoint.createRecord("", "", 8080, "");

        serviceDiscovery.publish(record, new Handler<AsyncResult<Record>>() {
            @Override
            public void handle(AsyncResult<Record> event) {

            }
        });
        serviceDiscovery.rxPublish(record);


        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("the-host")
                .setDatabase("the-db")
                .setUser("user")
                .setPassword("secret");

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        MySQLPool pool = MySQLPool.pool(connectOptions, poolOptions);


        pool.getConnection(new Handler<AsyncResult<SqlConnection>>() {
            @Override
            public void handle(AsyncResult<SqlConnection> event) {

                if (event.succeeded()) {

                    SqlConnection connection = event.result();

                    connection.query("");

                } else {

                }

            }
        });


        pool
                .query("SELECT * FROM users WHERE id='julien'")
                .execute(asyncResult -> {

                    if (asyncResult.succeeded()) {
                        RowSet<Row> result = asyncResult.result();


                        System.out.println("Got " + result.size() + " rows ");
                    } else {
                        System.out.println("Failure: " + asyncResult.cause().getMessage());
                    }

                    // Now close the pool
                    pool.close();

                });


        pool.getConnection(res -> {
            if (res.succeeded()) {

                // Transaction must use a connection
                SqlConnection conn = res.result();

                // Begin the transaction
                Transaction tx = conn.begin();

                // Various statements
                conn
                        .query("INSERT INTO Users (first_name,last_name) VALUES ('Julien','Viet')")
                        .execute(ar1 -> {
                            if (ar1.succeeded()) {
                                conn
                                        .query("INSERT INTO Users (first_name,last_name) VALUES ('Emad','Alblueshi')")
                                        .execute(ar2 -> {
                                            if (ar2.succeeded()) {
                                                tx.commit(ar3 -> {
                                                    if (ar3.succeeded()) {
                                                        System.out.println("Transaction succeeded");
                                                    } else {
                                                        System.out.println("Transaction failed " + ar3.cause().getMessage());
                                                    }
                                                    conn.close();
                                                });
                                            } else {
                                                conn.close();
                                            }
                                        });
                            } else {
                                conn.close();
                            }
                        });
            }
        });

//        Vertx.vertx().deployVerticle(new MainVerticle());

    }

}
