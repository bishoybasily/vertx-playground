package com.gmail.bishoybasily.demo_vertx.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-10
 */
@Data
@Accessors(chain = true)
public class Author {

    private String id;
    private String firstName;
    private String lastName;

}
