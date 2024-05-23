package io.quarkiverse.mongock.extension.deployment;

import io.quarkiverse.mongock.extension.runtime.MongockConfig;
import io.quarkiverse.mongock.extension.runtime.MongockProducer;
import io.quarkiverse.mongock.extension.runtime.MongockRecorder;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.deployment.BeanContainerListenerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.smallrye.health.deployment.spi.HealthBuildItem;

class MongockExtensionProcessor {

    private static final String FEATURE = "quarkus-mongock";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @Record(ExecutionTime.STATIC_INIT)
    @BuildStep
    public void build(BuildProducer<AdditionalBeanBuildItem> additionalBeanProducer, MongockRecorder recorder,
            MongockConfig config,
            BuildProducer<BeanContainerListenerBuildItem> containerListenerProducer) {

        AdditionalBeanBuildItem unremovableProducer = AdditionalBeanBuildItem.unremovableOf(MongockProducer.class);
        additionalBeanProducer.produce(unremovableProducer);

        containerListenerProducer.produce(new BeanContainerListenerBuildItem(recorder.setConfig(config)));

    }

    @BuildStep
    HealthBuildItem addReadinessHealthCheck(MongockConfig mongockConfig) {
        return new HealthBuildItem("com.rubean.mongock.extension.runtime.health.MongockReadinessHealthcheck",
                mongockConfig.healthEnabled);
    }

    @BuildStep
    HealthBuildItem addStartupHealthCheck(MongockConfig mongockConfig) {
        return new HealthBuildItem("com.rubean.mongock.extension.runtime.health.MongockStartupHealthcheck",
                mongockConfig.healthEnabled);
    }

    @Record(ExecutionTime.RUNTIME_INIT)
    @BuildStep
    void processMigration(MongockRecorder recorder, BeanContainerBuildItem beanContainer) {
        recorder.migrate(beanContainer.getValue());
    }

}
