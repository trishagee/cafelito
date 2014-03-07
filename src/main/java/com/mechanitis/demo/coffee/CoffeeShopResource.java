package com.mechanitis.demo.coffee;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Arrays;

import static java.util.Arrays.asList;

@Path("/coffeeshop")
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeShopResource {
    private final DB database;

    public CoffeeShopResource(final DB database) {
        this.database = database;
    }

    @Path("{id}/order/")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveOrder(@PathParam("id") long coffeeShopId, Order order) {
        order.setCoffeeShopId(coffeeShopId);

        DBCollection orders = database.getCollection("orders");
        JacksonDBCollection<Order, String> collection = JacksonDBCollection.wrap(orders, Order.class, String.class);

        WriteResult<Order, String> writeResult = collection.save(order);
        if (writeResult == null) {
            return Response.serverError().build();
        }
        order.setId(writeResult.getSavedId());

        return Response.created(URI.create(order.getId())).entity(order).build();
    }

    @Path("nearest/{latitude}/{longitude}")
    @GET
    public Object getNearest(@PathParam("latitude") double latitude, @PathParam("longitude") double longitude) {
        DBCollection collection = database.getCollection("coffeeshop");

        //{location: {$near: {$geometry: {type:        'Point',
        //                                coordinates: [longitude, latitude]},
        //                    $maxDistance: 2000}
        //           }
        //}
        DBObject coffeeShop = collection.findOne(new BasicDBObject("location",
                                                                   new BasicDBObject("$near",
                                                                                     new BasicDBObject("$geometry",
                                                                                                       new BasicDBObject("type", "Point")
                                                                                                       .append("coordinates",
                                                                                                               asList(longitude, latitude)))
                                                                                     .append("$maxDistance", 2000))));

        if (coffeeShop == null) {
            throw new WebApplicationException(404);
        }

        return coffeeShop;
    }



}
