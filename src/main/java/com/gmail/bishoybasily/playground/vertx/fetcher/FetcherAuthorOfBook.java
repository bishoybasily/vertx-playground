package com.gmail.bishoybasily.playground.vertx.fetcher;

import com.gmail.bishoybasily.playground.vertx.Main;
import com.gmail.bishoybasily.playground.vertx.model.entity.Author;
import com.gmail.bishoybasily.playground.vertx.model.entity.Book;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Objects;

/**
 * @author bishoybasily
 * @since 2020-04-10
 */
public class FetcherAuthorOfBook implements Fetcher<Author>, DataFetcher<Author> {

    @Override
    public Metadata getMetadata() {
        return Metadata.builder()
                .typeName("Book")
                .fieldName("author")
                .build();
    }

    @Override
    public Author get(DataFetchingEnvironment environment) {
        Book book = environment.getSource();
        return Main.authors.stream().filter(it -> it.getId().equals(book.getAuthorId())).findFirst().orElseThrow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetcherAuthorOfBook that = (FetcherAuthorOfBook) o;
        return getMetadata().equals(that.getMetadata());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMetadata());
    }

}
