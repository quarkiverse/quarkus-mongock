package io.quarkiverse.mongock.extension.runtime;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.arc.runtime.BeanContainerListener;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class MongockRecorder {

    public BeanContainerListener setConfig(MongockConfig mongockConfig) {
        return beanContainer -> {
            MongockProducer producer = beanContainer.beanInstance(MongockProducer.class);
            producer.setMongoCkConfig(mongockConfig);
        };
    }

    public void migrate(BeanContainer container) {
        Mongock mongock = container.beanInstance(Mongock.class);
        mongock.migrate();
    }

}
