package com.gmail.bishoybasily.demo_vertx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-05
 */
@Data
@Accessors(chain = true)
public class User {

    private String id;
    private String name;

}
