package io.quarkiverse.mongock.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MongockResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/mongock")
                .then()
                .statusCode(200)
                .body(is("Hello mongock"));
    }
}
