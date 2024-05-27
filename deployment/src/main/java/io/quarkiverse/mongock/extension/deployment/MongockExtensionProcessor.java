package io.quarkiverse.mongock.extension.deployment;

import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;

import org.jboss.jandex.ClassType;
import org.jboss.jandex.DotName;

import com.mongodb.client.MongoClient;

import io.mongock.api.annotations.ChangeUnit;
import io.quarkiverse.mongock.extension.MongockFactory;
import io.quarkiverse.mongock.extension.runtime.MongockRecorder;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.*;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.InitTaskCompletedBuildItem;
import io.quarkus.deployment.builditem.ServiceStartBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.recording.RecorderContext;
import io.quarkus.mongodb.runtime.MongodbConfig;

@BuildSteps(onlyIf = MongockEnabled.class)
class MongockExtensionProcessor {

    private static final String FEATURE = "quarkus-mongock";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(STATIC_INIT)
    void build(
            MongockRecorder recorder,
            CombinedIndexBuildItem combinedIndex,
            RecorderContext context,
            BuildProducer<ReflectiveClassBuildItem> reflectiveClassProducer) {
        List<Class<?>> migrationClasses = new ArrayList<>();
        addMigrationClasses(combinedIndex, context, reflectiveClassProducer, migrationClasses);
        recorder.setMigrationClasses(migrationClasses);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void createBeans(MongockRecorder recorder,
            MongodbConfig mongodbConfig,
            BuildProducer<SyntheticBeanBuildItem> syntheticBeanBuildItemBuildProducer) {

        SyntheticBeanBuildItem.ExtendedBeanConfigurator configurator = SyntheticBeanBuildItem
                .configure(MongockFactory.class)
                .scope(Singleton.class)
                .setRuntimeInit()
                .unremovable()
                .addInjectionPoint(ClassType.create(DotName.createSimple(MongoClient.class)))
                .createWith(recorder.mongockFunction(mongodbConfig));

        syntheticBeanBuildItemBuildProducer.produce(configurator.done());
    }

    @BuildStep
    @Consume(BeanContainerBuildItem.class)
    @Record(ExecutionTime.RUNTIME_INIT)
    ServiceStartBuildItem startMongock(MongockRecorder recorder,
            BuildProducer<InitTaskCompletedBuildItem> initializationCompleteBuildItem) {
        recorder.doStartActions();
        initializationCompleteBuildItem.produce(new InitTaskCompletedBuildItem("mongock"));
        return new ServiceStartBuildItem("mongock");
    }

    private void addMigrationClasses(
            CombinedIndexBuildItem combinedIndex,
            RecorderContext context,
            BuildProducer<ReflectiveClassBuildItem> reflectiveClassProducer,
            List<Class<?>> migrationClasses) {

        combinedIndex.getIndex().getAnnotations(DotName.createSimple(ChangeUnit.class.getName())).stream()
                .map(annotationInstance -> annotationInstance.target().asClass())
                .forEach(classInfo -> {
                    migrationClasses.add(context.classProxy(classInfo.name().toString()));
                    reflectiveClassProducer.produce(
                            ReflectiveClassBuildItem
                                    .builder(classInfo.name().toString())
                                    .methods()
                                    .build());
                });
    }
}
