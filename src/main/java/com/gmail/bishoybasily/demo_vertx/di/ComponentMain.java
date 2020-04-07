package com.gmail.bishoybasily.demo_vertx.di;

import com.gmail.bishoybasily.demo_vertx.HelperSQL;
import dagger.Component;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.sqlclient.Pool;
import org.flywaydb.core.Flyway;

import java.util.Set;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@ScopeMain
@Component(modules = {
        ModuleVertx.class,
        ModuleClosure.class,
        ModuleHttpServer.class,
        ModuleSQL.class
})
public interface ComponentMain {

    Vertx vertx();

    HelperSQL helperSql();

    Pool pool();

    HttpServer httpServer();

    Set<Thread> closures();

    Flyway flyway();

    class Factory {

        private static ComponentMain componentMain;

        public static ComponentMain instance() {

            if (componentMain == null)
                synchronized (Factory.class) {
                    if (componentMain == null)
                        componentMain = DaggerComponentMain.builder().build();
                }

            return componentMain;
        }

    }

}
