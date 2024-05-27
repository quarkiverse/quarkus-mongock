package io.quarkiverse.mongock.it;

import static io.restassured.RestAssured.get;

import java.util.List;

import io.restassured.common.mapper.TypeRef;

public class MongockTestUtils {

    static List<Fruit> listFruits() {
        return get("/fruits").as(new TypeRef<>() {
        });
    }
}
