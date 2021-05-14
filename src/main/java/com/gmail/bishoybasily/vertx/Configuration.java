package com.gmail.bishoybasily.vertx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2021-05-14
 */
@Data
@Accessors(chain = true)
public class Configuration {

    private Http http;

    @Data
    @Accessors(chain = true)
    public static class Http {

        private Integer port;

    }

}
