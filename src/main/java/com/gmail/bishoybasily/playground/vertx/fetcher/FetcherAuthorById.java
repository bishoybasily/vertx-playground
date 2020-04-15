package com.gmail.bishoybasily.playground.vertx.fetcher;

import com.gmail.bishoybasily.playground.vertx.Main;
import com.gmail.bishoybasily.playground.vertx.model.entity.Author;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Objects;

/**
 * @author bishoybasily
 * @since 2020-04-10
 */
public class FetcherAuthorById implements Fetcher<Author>, DataFetcher<Author> {

    @Override
    public Metadata getMetadata() {
        return Metadata.builder()
                .typeName("Query")
                .fieldName("authorById")
                .build();
    }

    @Override
    public Author get(DataFetchingEnvironment environment) {
        String id = environment.getArgument("id");
        return Main.authors.stream().filter(it -> it.getId().equals(id)).findFirst().orElseThrow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetcherAuthorById that = (FetcherAuthorById) o;
        return getMetadata().equals(that.getMetadata());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMetadata());
    }

}
