package com.gmail.bishoybasily.demo_vertx.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.sqlclient.Pool;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@Module
public class ModuleClosure {

    @IntoSet
    @Provides
    public Thread threadPoolClosure(Pool arg) {
        return new Thread(() -> {
            arg.close();
            System.out.println("Pool closed");
        });
    }

    @IntoSet
    @Provides
    public Thread threadHttpServerClosure(HttpServer arg) {
        return new Thread(() -> {
            arg.close();
            System.out.println("HttpServer closed");
        });
    }

    @IntoSet
    @Provides
    public Thread threadVertxClosure(Vertx arg) {
        return new Thread(() -> {
            arg.close();
            System.out.println("Vertx closed");
        });
    }

}