package io.quarkiverse.mongock.it;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.mongock.runner.core.executor.MongockRunner;
import io.quarkiverse.mongock.MongockFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    MongockFactory mongockFactory;

    @GET
    @Path("fruits")
    public List<Fruit> list() {
        return Fruit.listAll();
    }

    @POST
    @Path("migrate")
    public void migrate() {
        MongockRunner mongockRunner = mongockFactory.createMongockRunner();
        mongockRunner.execute();
    }
}
