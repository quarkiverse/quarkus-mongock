package io.quarkiverse.mongock.extension.runtime.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import io.quarkiverse.mongock.extension.runtime.Mongock;

@Startup
@ApplicationScoped
public class MongockStartupHealthcheck implements HealthCheck {

    @Inject
    Mongock mongock;

    @Override
    public HealthCheckResponse call() {
        if (mongock.isSkipped()) {
            return HealthCheckResponse.builder().name("Mongock Startup").withData("migration", "skipped").up().build();
        }
        if (mongock.isSuccess()) {
            return HealthCheckResponse.builder().name("Mongock Startup").withData("migration", "executed").up().build();
        } else {
            return HealthCheckResponse.builder().name("Mongock Startup").withData("migration", "failed").up().build();
        }
    }

}
