package com.gmail.bishoybasily.playground.vertx.di;

import com.gmail.bishoybasily.playground.vertx.HelperSQL;
import com.gmail.bishoybasily.playground.vertx.config.ConfigurationSQL;
import dagger.Module;
import dagger.Provides;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import org.flywaydb.core.Flyway;

/**
 * @author bishoybasily
 * @since 2020-04-07
 */
@Module
public class ModuleSQL {

    @ScopeMain
    @Provides
    public Flyway flyway(ConfigurationSQL conf) {
        return Flyway.configure()
                .dataSource(conf.url(), conf.username(), conf.password())
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .load();
    }

    @ScopeMain
    @Provides
    public MySQLConnectOptions mySQLConnectOptions(ConfigurationSQL conf) {
        return new MySQLConnectOptions()
                .setUseAffectedRows(true)
                .setPort(conf.port())
                .setHost(conf.host())
                .setDatabase(conf.database())
                .setUser(conf.username())
                .setPassword(conf.password());
    }

    @ScopeMain
    @Provides
    public PoolOptions poolOptions() {
        return new PoolOptions()
                .setMaxSize(15);
    }

    @ScopeMain
    @Provides
    public Pool pool(MySQLConnectOptions mySQLConnectOptions, PoolOptions poolOptions) {
        return MySQLPool.pool(mySQLConnectOptions, poolOptions);
    }

    @ScopeMain
    @Provides
    public HelperSQL helperSQL(Pool pool) {
        return new HelperSQL(pool);
    }

    @ScopeMain
    @Provides
    public ConfigurationSQL configurationSQL() {
        return ConfigurationSQL.builder()
                .host("127.0.0.1")
                .port(3306)
                .username("root")
                .password("toor")
                .database("simple_castle_accounts")
                .build();
    }

}
