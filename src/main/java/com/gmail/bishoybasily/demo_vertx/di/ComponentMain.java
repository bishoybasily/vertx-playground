package com.gmail.bishoybasily.demo_vertx.di;

import dagger.Component;
import org.flywaydb.core.Flyway;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@ScopeMain
@Component(modules = {
        ModuleVertx.class,
        ModuleClosure.class,
        ModuleHttpServer.class,
        ModuleSQL.class
})
public interface ComponentMain {

    Flyway flyway();

    Set<Thread> closures();

    class Factory {

        private static ComponentMain componentMain;

        public static ComponentMain instance() {

            if (componentMain == null)
                synchronized (Factory.class) {
                    if (componentMain == null)
                        componentMain = DaggerComponentMain.builder().build();
                }

            return componentMain;
        }

        public static void run(Consumer<ComponentMain>... consumers) {
            Stream.of(consumers).forEach(c -> c.accept(instance()));
        }

    }

}
