package io.quarkiverse.mongock.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "quarkus.mongock")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface MongockRuntimeConfig {

    /**
     * {@code true} to execute Mongock automatically when the application starts, {@code false} otherwise.
     */
    @WithDefault("false")
    boolean migrateAtStart();

    /**
     * {@code true} to enable transaction, {@code false} otherwise. If the driver does not support transaction, it will be
     * automatically disabled.
     */
    @WithDefault("true")
    boolean transactionEnabled();

}
