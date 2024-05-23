package io.quarkiverse.mongock.extension.runtime;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;

import io.mongock.driver.mongodb.sync.v4.driver.MongoSync4Driver;
import io.mongock.runner.core.event.MigrationFailureEvent;
import io.mongock.runner.core.event.MigrationStartedEvent;
import io.mongock.runner.core.event.MigrationSuccessEvent;
import io.mongock.runner.core.executor.MongockRunner;
import io.mongock.runner.standalone.MongockStandalone;

@ApplicationScoped
public class Mongock {

    private MongockRunner runner;
    private boolean success = false;
    private MongockConfig config;

    private static final Logger LOG = Logger.getLogger(Mongock.class);

    public Mongock(MongoClient client, MongockConfig config) {
        this.config = config;

        String connectionString = ConfigProvider.getConfig().getValue("quarkus.mongodb.connection-string", String.class);
        ConnectionString connString = new ConnectionString(connectionString);
        String databaseName = connString.getDatabase();
        LOG.info("produceMongoCk.. using databaseName: " + databaseName + " and mongoclient: " + client + " and scanpackage: "
                + config.scanPackage);

        this.runner = MongockStandalone.builder()
                .setDriver(MongoSync4Driver.withDefaultLock(client, databaseName))
                .addMigrationScanPackage(config.scanPackage)
                .setMigrationSuccessListener(this::onSuccess)
                .setMigrationStartedListener(this::onStart)
                .setMigrationFailureListener(this::onFail)
                .setEnabled(config.extensionEnabled)
                .buildRunner();
    }

    public boolean inProgress() {
        return runner.isExecutionInProgress();
    }

    public void migrate() {
        try {
            runner.execute();
        } catch (Exception e) {
            LOG.error(e);
            success = false;
        }
    }

    private void onStart(MigrationStartedEvent event) {
        LOG.info("[EVENT LISTENER] - Mongock STARTED successfully");
    }

    private void onSuccess(MigrationSuccessEvent event) {
        LOG.info("[EVENT LISTENER] - Mongock finished successfully");
        success = true;
    }

    private void onFail(MigrationFailureEvent event) {
        LOG.error(
                "[EVENT LISTENER] - Mongock finished with failures: " + event.getMigrationResult().getException().getMessage());
        success = false;

    }

    public boolean isSkipped() {
        return !config.extensionEnabled;
    }

    public boolean isSuccess() {
        return success;
    }

}
