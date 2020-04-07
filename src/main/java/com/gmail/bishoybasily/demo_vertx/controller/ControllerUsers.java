package com.gmail.bishoybasily.demo_vertx.controller;

import com.gmail.bishoybasily.demo_vertx.service.ServiceUsers;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.core.http.HttpServerResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@RequiredArgsConstructor
public class ControllerUsers implements Controller {

    private final ServiceUsers serviceUsers;

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
        serviceUsers.read().subscribe(it -> response.end(Json.encode(it)));
    }

}
