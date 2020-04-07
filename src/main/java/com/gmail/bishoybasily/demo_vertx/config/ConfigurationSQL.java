package com.gmail.bishoybasily.demo_vertx.config;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@Data
@Builder
@Accessors(fluent = true)
public class ConfigurationSQL {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String database;

    public String url() {
        return String.format("jdbc:mysql://%s:%d/%s?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true", host, port, database);
    }

}
