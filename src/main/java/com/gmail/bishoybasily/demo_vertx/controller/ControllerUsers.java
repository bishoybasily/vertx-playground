package com.gmail.bishoybasily.demo_vertx.controller;

import com.gmail.bishoybasily.demo_vertx.User;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.core.http.HttpServerResponse;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
public class ControllerUsers implements Controller {

    @Override
    public Metadata getMetadata() {
        return Metadata.builder()
                .method(HttpMethod.GET)
                .path("/api/users")
                .produces("application/json")
                .build();
    }

    @Override
    public void handle(HttpServerRequest request, HttpServerResponse response) {
        response.end(Json.encode(new User().setName("bishoy").setId("123")));
    }

}
