package com.gmail.bishoybasily.playground.vertx.controller;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
public interface Controller {

    Metadata getMetadata();

    default Handler<RoutingContext> getHandler() {
        return event -> handle(event.request(), event.response());
    }

    void handle(HttpServerRequest request, HttpServerResponse response);

    @Data
    @EqualsAndHashCode(of = {"method", "path"})
    @Accessors(fluent = true)
    @Builder
    class Metadata {

        private HttpMethod method;
        private String path;
        private String consumes;
        private String produces;

    }

}
