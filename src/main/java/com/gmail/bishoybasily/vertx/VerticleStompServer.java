package com.gmail.bishoybasily.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.stomp.StompServer;
import io.vertx.ext.stomp.StompServerHandler;

/**
 * @author bishoybasily
 * @since 2021-05-13
 */
public class VerticleStompServer extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) {

        startStompServer()
                .onSuccess(it -> {
                    promise.complete();
                })
                .onFailure(promise::fail);

    }

    private Future<StompServer> startStompServer() {
        return StompServer.create(vertx)
                .handler(StompServerHandler.create(vertx))
                .listen(9090);
    }

}
