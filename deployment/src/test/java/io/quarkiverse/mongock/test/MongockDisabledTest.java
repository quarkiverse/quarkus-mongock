package io.quarkiverse.mongock.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkiverse.mongock.MongockFactory;
import io.quarkus.test.QuarkusUnitTest;

public class MongockDisabledTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.mongock.enabled", "false")
            .withEmptyApplication();

    @Inject
    Instance<MongockFactory> bean;

    @Test
    void testNoMongockFactoryInstance() {
        assertTrue(bean.isUnsatisfied(), "No mongock factory bean");
    }
}
