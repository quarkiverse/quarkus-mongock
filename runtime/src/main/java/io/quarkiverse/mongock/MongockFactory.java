package io.quarkiverse.mongock;

import java.util.List;

import com.mongodb.client.MongoClient;

import io.mongock.driver.mongodb.sync.v4.driver.MongoSync4Driver;
import io.mongock.runner.core.executor.MongockRunner;
import io.mongock.runner.standalone.MongockStandalone;
import io.quarkiverse.mongock.runtime.MongockRuntimeConfig;
import io.quarkus.mongodb.runtime.MongodbConfig;

public class MongockFactory {

    private final MongoClient mongoClient;

    private final MongodbConfig mongodbConfig;

    private final MongockRuntimeConfig mongockRuntimeConfig;
    private final List<Class<?>> migrationClasses;

    public MongockFactory(
            MongoClient mongoClient,
            MongodbConfig mongodbConfig,
            MongockRuntimeConfig mongockRuntimeConfig,
            List<Class<?>> migrationClasses) {

        this.mongoClient = mongoClient;
        this.mongodbConfig = mongodbConfig;
        this.mongockRuntimeConfig = mongockRuntimeConfig;
        this.migrationClasses = migrationClasses;
    }

    public MongockRunner createMongockRunner() {
        String databaseName = mongodbConfig.defaultMongoClientConfig().database()
                .orElseThrow(() -> new IllegalStateException("The database property was not configured for " +
                        "the default Mongo Client (via 'quarkus.mongodb.database')"));

        return MongockStandalone.builder()
                .setDriver(
                        MongoSync4Driver.withDefaultLock(mongoClient, databaseName))
                .addMigrationClasses(migrationClasses)
                .setTransactional(mongockRuntimeConfig.transactionEnabled())
                // See https://github.com/mongock/mongock/issues/661
                .setLockGuardEnabled(false)
                .buildRunner();
    }

}
