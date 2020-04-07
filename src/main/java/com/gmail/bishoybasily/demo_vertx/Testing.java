package com.gmail.bishoybasily.demo_vertx;


import com.gmail.bishoybasily.demo_vertx.di.ComponentMain;

/**
 * @author bishoybasily
 * @since 2020-04-05
 */
public class Testing {

    public static void main(String[] args) {
        ComponentMain.Factory.instance().flyway().migrate();
        ComponentMain.Factory.instance().closures().forEach(Runtime.getRuntime()::addShutdownHook);
    }

}
