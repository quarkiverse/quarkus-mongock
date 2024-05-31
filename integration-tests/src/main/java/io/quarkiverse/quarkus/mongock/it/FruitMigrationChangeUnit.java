package io.quarkiverse.quarkus.mongock.it;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "fruitMigrationChangeUnit", order = "001", author = "mongock_test", systemVersion = "1")
public class FruitMigrationChangeUnit {
    @Execution
    public void migrationMethod() {
        Fruit.persist(new Fruit("apple"));
    }

    @RollbackExecution
    public void rollback() {
        Fruit.deleteAll();
    }
}