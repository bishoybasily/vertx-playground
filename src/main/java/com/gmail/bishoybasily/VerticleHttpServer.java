package com.gmail.bishoybasily;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * @author bishoybasily
 * @since 2021-05-13
 */
public class VerticleHttpServer extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) {

        startHttpServer()
                .onSuccess(it -> {
                    vertx.deployVerticle(VerticleWelcome.class.getName(), new DeploymentOptions().setConfig(config()));
                    promise.complete();
                })
                .onFailure(promise::fail);

    }

    private Future<HttpServer> startHttpServer() {
        var router = Router.router(vertx);

        router.route().handler(LoggerHandler.create());
        router.route().handler(CorsHandler.create());

        router.get("/api/welcome").handler(ctx -> {
            vertx.eventBus().<String>request("api.welcome", "", reply -> {
                ctx.response().end(reply.result().body());
            });
        });

        var configuration = Json.decodeValue(config().encode(), Configuration.class);

        return vertx.createHttpServer()
                .requestHandler(router)
                .listen(configuration.getHttp().getPort());
    }

}
