package io.quarkiverse.mongock.extension.runtime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.mongodb.client.MongoClient;

@ApplicationScoped
public class MongockProducer {

    @Inject
    MongoClient client;

    private MongockConfig mongockConfig;

    public void setMongoCkConfig(MongockConfig mongockConfig) {
        this.mongockConfig = mongockConfig;
    }

    @Produces
    @Singleton
    public Mongock produceMongo() throws Exception {
        return new Mongock(client, mongockConfig);
    }

}
