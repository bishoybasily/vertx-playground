package com.gmail.bishoybasily;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import lombok.SneakyThrows;

import java.util.concurrent.Executors;

public class VerticleMain extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) {

        Executors.newWorkStealingPool().submit(new Runnable() {

            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(5000);
                vertx.close();
            }

        });

        getConfiguration()
                .map(it -> new DeploymentOptions().setConfig(it))
                .onSuccess(options -> {
                    vertx.deployVerticle(VerticleHttpServer.class.getName(), options);
                    vertx.deployVerticle(VerticleStompServer.class.getName(), options);
                    promise.complete();
                })
                .onFailure(promise::fail);

    }

    private Future<JsonObject> getConfiguration() {

        var configRetrieverOptions = new ConfigRetrieverOptions()
                .addStore(
                        new ConfigStoreOptions()
                                .setType("file")
                                .setFormat("yaml")
                                .setConfig(
                                        new JsonObject()
                                                .put("path", "application.yaml")
                                )
                );

        var configRetriever = ConfigRetriever.create(vertx, configRetrieverOptions);

        return configRetriever.getConfig();
    }

}




