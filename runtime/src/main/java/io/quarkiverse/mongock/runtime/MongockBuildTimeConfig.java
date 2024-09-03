package io.quarkiverse.mongock.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "quarkus.mongock")
@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public interface MongockBuildTimeConfig {

    /**
     * Whether Mongock is enabled *during the build*.
     *
     * If Mongock is disabled, the Mongock beans won't be created and Mongock won't be usable.
     *
     * @asciidoclet
     */
    @WithDefault("true")
    boolean enabled();
}
