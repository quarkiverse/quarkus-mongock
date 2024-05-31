package io.quarkiverse.quarkus.mongock.it;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "fruits")
public class Fruit extends PanacheMongoEntity {
    public String name;

    public Fruit() {
    }

    public Fruit(String name) {
        this.name = name;
    }
}
