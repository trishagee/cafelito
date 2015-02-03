package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static java.net.URI.create;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.created;

@Path("/coffeeshop/")
@Produces(APPLICATION_JSON)
public class CoffeeShopResource {
    private Datastore datastore;

    public CoffeeShopResource(MongoClient mongoClient) {
        this.datastore = new Morphia().createDatastore(mongoClient, "Cafelito");
    }

    @Path("order")
    @POST()
    @Consumes(APPLICATION_JSON)
    public Response saveOrder(Order order) {
        datastore.save(order);

        return created(create(order.getId())).entity(order).build();
    }
}
