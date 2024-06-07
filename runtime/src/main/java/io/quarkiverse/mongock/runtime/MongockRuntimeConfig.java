package io.quarkiverse.mongock.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "mongock", phase = ConfigPhase.RUN_TIME)
public final class MongockRuntimeConfig {

    /**
     * {@code true} to execute Mongock automatically when the application starts, {@code false} otherwise.
     */
    @ConfigItem
    public boolean migrateAtStart;

    /**
     * {@code true} to enable transaction, {@code false} otherwise. If the driver does not support transaction, it will be
     * automatically disabled.
     */
    @ConfigItem(defaultValue = "true")
    public boolean transactionEnabled;

}