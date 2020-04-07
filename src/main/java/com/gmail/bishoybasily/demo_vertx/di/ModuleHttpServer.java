package com.gmail.bishoybasily.demo_vertx.di;

import com.gmail.bishoybasily.demo_vertx.controller.Controller;
import com.gmail.bishoybasily.demo_vertx.controller.ControllerUsers;
import com.gmail.bishoybasily.demo_vertx.service.ServiceUsers;
import com.gmail.bishoybasily.demo_vertx.utils.ObjectUtils;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Route;
import io.vertx.reactivex.ext.web.Router;

import java.util.Set;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@Module
public class ModuleHttpServer {

    @ScopeMain
    @Provides
    public HttpServer httpServer(Vertx vertx, Router router) {
        return vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }

    @ScopeMain
    @Provides
    public Router router(Vertx vertx, Set<Controller> controllers) {
        Router router = Router.router(vertx);

        controllers.forEach(controller -> {

            Controller.Metadata metadata = controller.getMetadata();

            Route route = router.route(metadata.method(), metadata.path())
                    .handler(event -> controller.handle(event.request(), event.response()));

            if (!ObjectUtils.isEmpty(metadata.consumes()))
                route.consumes(metadata.consumes());
            if (!ObjectUtils.isEmpty(metadata.produces()))
                route.produces(metadata.produces());

        });

        return router;
    }

    @ScopeMain
    @IntoSet
    @Provides
    public Controller controllerUsers(ServiceUsers serviceUsers) {
        return new ControllerUsers(serviceUsers);
    }

}
