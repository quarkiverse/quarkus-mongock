package io.quarkiverse.quarkus.mongock.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "mongock", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public final class MongockBuildTimeConfig {

    /**
     * Whether Mongock is enabled *during the build*.
     *
     * If Mongock is disabled, the Mongock beans won't be created and Mongock won't be usable.
     *
     * @asciidoclet
     */
    @ConfigItem(defaultValue = "true")
    public boolean enabled;
}