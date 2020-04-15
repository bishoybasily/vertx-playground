package com.gmail.bishoybasily.playground.vertx;


import com.gmail.bishoybasily.playground.vertx.di.ComponentMain;
import com.gmail.bishoybasily.playground.vertx.model.entity.Author;
import com.gmail.bishoybasily.playground.vertx.model.entity.Book;

import java.util.HashSet;
import java.util.Set;

/**
 * @author bishoybasily
 * @since 2020-04-05
 */
public class Main {

    public final static Set<Book> books = new HashSet<>();
    public final static Set<Author> authors = new HashSet<>();

    static {

        for (int i = 0; i < 5; i++) {

            Author author = new Author()
                    .setId("id_" + i)
                    .setFirstName("auth " + i + " f name")
                    .setLastName("auth " + i + " l name");

            authors.add(author);

            for (int j = 0; j < 2; j++) {

                Book book = new Book()
                        .setId("id_" + j)
                        .setName("auth " + i + " book " + j)
                        .setAuthorId(author.getId());

                books.add(book);

            }

        }

    }


    /**
     * kindly not that, the consumers sequence matters
     *
     * @param args
     */
    public static void main(String[] args) {
        ComponentMain.Initializer.run(
                it -> it.flyway().migrate(),
                it -> it.closures().forEach(Runtime.getRuntime()::addShutdownHook)
        );
    }

}
