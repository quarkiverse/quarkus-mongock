package io.quarkiverse.mongock.extension.deployment;

import java.util.function.BooleanSupplier;

import io.quarkiverse.mongock.extension.runtime.MongockBuildTimeConfig;

/**
 * Supplier that can be used to only run build steps
 * if the Mongock extension is enabled.
 */
public class MongockEnabled implements BooleanSupplier {

    private final MongockBuildTimeConfig config;

    MongockEnabled(MongockBuildTimeConfig config) {
        this.config = config;
    }

    @Override
    public boolean getAsBoolean() {
        return config.enabled;
    }

}
