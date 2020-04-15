package com.gmail.bishoybasily.playground.vertx.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-10
 */
@Data
@Accessors(chain = true)
public class Book {

    private String id;
    private String name;
    private String authorId;

}
