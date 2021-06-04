package com.gmail.bishoybasily;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.providers.KeycloakAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.OAuth2AuthHandler;

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

        var loggerHandler = LoggerHandler.create();
        var corsHandler = CorsHandler.create();
        var oAuth2AuthHandler = OAuth2AuthHandler.create(vertx, KeycloakAuth.create(vertx, OAuth2FlowType.AUTH_CODE, new JsonObject("{\n" +
                "  \"realm\": \"master\",\n" +
                "  \"auth-server-url\": \"http://127.0.0.1:38080/auth/\",\n" +
                "  \"ssl-required\": \"external\",\n" +
                "  \"resource\": \"vertx\",\n" +
                "  \"credentials\": {\n" +
                "    \"secret\": \"e44d6419-890b-4889-9323-b61b2dc2aa96\"\n" +
                "  },\n" +
                "  \"confidential-port\": 0\n" +
                "}")), "http://localhost:8080/keycloak")
                .setupCallback(router.get("/keycloak"));

        router.route().handler(loggerHandler);
        router.route().handler(corsHandler);
        router.route("/private/*").handler(oAuth2AuthHandler);

        router.get("/public/welcome").handler(ctx -> {
            vertx.eventBus().<String>request("public.welcome", "", reply -> {
                ctx.response().end(reply.result().body());
            });
        });
        router.get("/private/welcome").handler(ctx -> {
            vertx.eventBus().<String>request("private.welcome", "", reply -> {
                ctx.response().end(reply.result().body());
            });
        });

        var configuration = Json.decodeValue(config().encode(), Configuration.class);

        return vertx.createHttpServer()
                .requestHandler(router)
                .listen(configuration.getHttp().getPort());
    }

}
