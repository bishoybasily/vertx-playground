package com.gmail.bishoybasily.playground.vertx.di;

import com.gmail.bishoybasily.playground.vertx.controller.Controller;
import com.gmail.bishoybasily.playground.vertx.controller.ControllerUsers;
import com.gmail.bishoybasily.playground.vertx.fetcher.Fetcher;
import com.gmail.bishoybasily.playground.vertx.fetcher.FetcherAuthorById;
import com.gmail.bishoybasily.playground.vertx.fetcher.FetcherAuthorOfBook;
import com.gmail.bishoybasily.playground.vertx.fetcher.FetcherBookById;
import com.gmail.bishoybasily.playground.vertx.model.service.ServiceUsers;
import com.gmail.bishoybasily.playground.vertx.utils.ObjectUtils;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import graphql.GraphQL;
import graphql.schema.idl.*;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import io.vertx.ext.web.handler.graphql.GraphiQLHandler;
import io.vertx.ext.web.handler.graphql.GraphiQLHandlerOptions;

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
    public Router router(Vertx vertx, GraphQL graphQL, GraphiQLHandlerOptions graphiQLHandlerOptions, Set<Controller> controllers) {
        Router router = Router.router(vertx);

        controllers.forEach(controller -> {
            Controller.Metadata metadata = controller.getMetadata();
            Route route = router.route(metadata.method(), metadata.path()).handler(controller.getHandler());
            if (!ObjectUtils.isEmpty(metadata.consumes()))
                route.consumes(metadata.consumes());
            if (!ObjectUtils.isEmpty(metadata.produces()))
                route.produces(metadata.produces());
        });

        router.post("/graphql").handler(GraphQLHandler.create(graphQL));
        router.route("/graphiql/*").handler(GraphiQLHandler.create(graphiQLHandlerOptions));

        return router;
    }

    @ScopeMain
    @Provides
    public GraphQL graphQL(SchemaGenerator generator, TypeDefinitionRegistry registry, RuntimeWiring wiring) {
        return GraphQL.newGraphQL(generator.makeExecutableSchema(registry, wiring)).build();
    }

    @ScopeMain
    @Provides
    public RuntimeWiring runtimeWiring(Set<Fetcher<?>> fetchers) {
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        fetchers.forEach(fetcher -> {
            Fetcher.Metadata metadata = fetcher.getMetadata();
            builder.type(TypeRuntimeWiring.newTypeWiring(metadata.typeName()).dataFetcher(metadata.fieldName(), fetcher));
        });
        return builder.build();
    }

    @IntoSet
    @ScopeMain
    @Provides
    public Fetcher<?> fetcherBookById() {
        return new FetcherBookById();
    }

    @IntoSet
    @ScopeMain
    @Provides
    public Fetcher<?> fetcherAuthorById() {
        return new FetcherAuthorById();
    }

    @IntoSet
    @ScopeMain
    @Provides
    public Fetcher<?> fetcherAuthorOfBook() {
        return new FetcherAuthorOfBook();
    }

    @ScopeMain
    @Provides
    public TypeDefinitionRegistry typeDefinitionRegistry() {
        return new SchemaParser().parse(getClass().getResourceAsStream("/gql/schema/schema.graphqls"));
    }

    @ScopeMain
    @Provides
    public SchemaGenerator schemaGenerator() {
        return new SchemaGenerator();
    }

    @ScopeMain
    @Provides
    public GraphiQLHandlerOptions graphiQLHandlerOptions() {
        return new GraphiQLHandlerOptions().setEnabled(true);
    }

    @ScopeMain
    @IntoSet
    @Provides
    public Controller controllerUsers(ServiceUsers serviceUsers) {
        return new ControllerUsers(serviceUsers);
    }

}
