package io.quarkiverse.mongock.extension.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * master change log file
 */
@ConfigRoot(name = MongockConfig.CONFIG_NAME, phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class MongockConfig {

    public static final String CONFIG_NAME = "mongock";

    /**
     * allows to set the scan package path for ChangeUnit annotation, see Mongock
     * documentation
     */
    @ConfigItem(name = "scan.package")
    public String scanPackage;

    /**
     * allows to disable the mongock extension
     */
    @ConfigItem(name = "enabled")
    public boolean extensionEnabled;

    /**
     * allows to disable the healthchecks for the mongock extension
     */
    @ConfigItem(name = "health.enabled", defaultValue = "true")
    public boolean healthEnabled;
//
}
