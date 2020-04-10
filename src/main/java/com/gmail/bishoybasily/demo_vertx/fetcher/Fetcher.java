package com.gmail.bishoybasily.demo_vertx.fetcher;

import graphql.schema.DataFetcher;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author bishoybasily
 * @since 2020-04-10
 */
public interface Fetcher<T> extends DataFetcher<T> {

    Metadata getMetadata();

    @Data
    @EqualsAndHashCode(of = {"typeName", "fieldName"})
    @Accessors(fluent = true)
    @Builder
    class Metadata {

        private String typeName;
        private String fieldName;

    }

}
