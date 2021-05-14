package com.gmail.bishoybasily.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.util.UUID;

/**
 * @author bishoybasily
 * @since 2021-05-13
 */
public class VerticleWelcome extends AbstractVerticle {

    private final String id = UUID.randomUUID().toString();

    @Override
    public void start(Promise<Void> promise) {

        vertx.eventBus().consumer("api.welcome", it -> {
            it.reply(String.format("Hello from vertx another verticle typed threaded %s", id));
        });

        promise.complete();

    }
}
