package com.gmail.bishoybasily.playground.vertx.controller;

import com.gmail.bishoybasily.playground.vertx.model.service.ServiceUsers;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
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
        serviceUsers.read().collectList().map(Json::encode).subscribe(response::end, err -> response.end(err.getMessage()));
    }

}
