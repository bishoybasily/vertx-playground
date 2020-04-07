package com.gmail.bishoybasily.demo_vertx.controller;

import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.core.http.HttpServerResponse;
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

    default void handle(HttpServerRequest request, HttpServerResponse response) {

    }

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
