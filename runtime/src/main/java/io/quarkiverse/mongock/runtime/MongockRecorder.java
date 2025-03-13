package io.quarkiverse.mongock.runtime;

import java.util.List;
import java.util.function.Function;

import org.jboss.logging.Logger;

import com.mongodb.client.MongoClient;

import io.mongock.runner.core.executor.MongockRunner;
import io.quarkiverse.mongock.MongockFactory;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.arc.SyntheticCreationalContext;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class MongockRecorder {

    private static final Logger log = Logger.getLogger(MongockRecorder.class);

    private final RuntimeValue<MongockRuntimeConfig> config;

    public static volatile List<Class<?>> migrationClasses;

    public MongockRecorder(RuntimeValue<MongockRuntimeConfig> config) {
        this.config = config;
    }

    public void setMigrationClasses(List<Class<?>> migrationClasses) {
        log.debugv("Setting the following migration classes: {0}", migrationClasses);
        MongockRecorder.migrationClasses = migrationClasses;
    }

    public Function<SyntheticCreationalContext<MongockFactory>, MongockFactory> mongockFunction() {
        return (SyntheticCreationalContext<MongockFactory> context) -> {
            MongoClient mongoClient = context.getInjectedReference(MongoClient.class);
            return new MongockFactory(mongoClient, config.getValue(), migrationClasses);
        };
    }

    public void doStartActions() {
        InstanceHandle<MongockFactory> mongockFactoryInstanceHandle = Arc.container().instance(MongockFactory.class);

        if (!mongockFactoryInstanceHandle.isAvailable()) {
            return;
        }

        MongockFactory mongockFactory = mongockFactoryInstanceHandle.get();
        MongockRunner mongockRunner = mongockFactory.createMongockRunner();

        if (config.getValue().migrateAtStart()) {
            mongockRunner.execute();
        }
    }

}
