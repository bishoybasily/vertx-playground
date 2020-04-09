package com.gmail.bishoybasily.demo_vertx;


import com.gmail.bishoybasily.demo_vertx.di.ComponentMain;

/**
 * @author bishoybasily
 * @since 2020-04-05
 */
public class Testing {

    /**
     * kindly not that, the consumers sequence matters
     *
     * @param args
     */
    public static void main(String[] args) {
        ComponentMain.Factory.run(
                it -> it.flyway().migrate(),
                it -> it.closures().forEach(Runtime.getRuntime()::addShutdownHook)
        );
    }

}
