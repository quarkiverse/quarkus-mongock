package io.quarkiverse.mongock.it;

import static io.quarkiverse.mongock.it.MongockTestUtils.listFruits;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;

@TestProfile(value = MongockMigrateAtStartTest.MigrateAtStartTestProfile.class)
@QuarkusTest
public class MongockMigrateAtStartTest {

    @Test
    public void testMigrateAtStart() {
        Assertions.assertEquals(1, listFruits().size());
    }

    public static class MigrateAtStartTestProfile implements QuarkusTestProfile {

        @Override
        public Map<String, String> getConfigOverrides() {
            return Map.of("quarkus.mongock.migrate-at-start", "true");
        }
    }
}
