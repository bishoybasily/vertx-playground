package com.gmail.bishoybasily;

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

        vertx.eventBus().consumer("public.welcome", it -> {
            it.reply(String.format("Hello from vertx another verticle typed threaded %s", id));
        });
        vertx.eventBus().consumer("private.welcome", it -> {
            it.reply(String.format("This is a very secured api, how did you get there! %s", id));
        });

        promise.complete();

    }
}
