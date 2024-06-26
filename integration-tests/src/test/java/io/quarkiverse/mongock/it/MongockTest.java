package io.quarkiverse.mongock.it;

import static io.restassured.RestAssured.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MongockTest {

    @Test
    public void testMigration() {
        Assertions.assertEquals(0, MongockTestUtils.listFruits().size());
        post("/migrate");
        Assertions.assertEquals(1, MongockTestUtils.listFruits().size());
    }
}
