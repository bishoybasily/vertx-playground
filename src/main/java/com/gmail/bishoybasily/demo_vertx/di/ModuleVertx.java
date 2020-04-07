package com.gmail.bishoybasily.demo_vertx.di;

import dagger.Module;
import dagger.Provides;
import io.vertx.reactivex.core.Vertx;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@Module
public class ModuleVertx {

    @ScopeMain
    @Provides
    public Vertx vertx() {
        return Vertx.vertx();
    }

}
