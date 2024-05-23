package io.quarkiverse.mongock.extension.runtime.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import io.quarkiverse.mongock.extension.runtime.Mongock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Readiness
@ApplicationScoped
public class MongockReadinessHealthcheck implements HealthCheck {

    @Inject
    Mongock mongock;

    @Override
    public HealthCheckResponse call() {
        if (mongock.isSkipped()) {
            return HealthCheckResponse.builder().name("Mongock Readiness").withData("migration", "skipped").withData("in-progress", mongock.inProgress()).up().build();
        }
        if (mongock.isSuccess()) {
            return HealthCheckResponse.builder().name("Mongock Readiness").withData("migration", "executed").withData("in-progress", mongock.inProgress()).up().build();
        } else {
            return HealthCheckResponse.builder().name("Mongock Readiness").withData("migration", "failed").withData("in-progress", mongock.inProgress()).down().build();
        }
    }

}
