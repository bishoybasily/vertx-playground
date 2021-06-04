package com.gmail.bishoybasily;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2021-05-14
 */
@Data
@Accessors(chain = true)
public class Configuration {

    private Service http;
    private Service stomp;

    @Data
    @Accessors(chain = true)
    public static class Service {

        private Integer port;

    }

}
