package com.mechanitis.demo.coffee

import com.mongodb.MongoClient
import org.bson.types.ObjectId
import spock.lang.Ignore
import spock.lang.Specification

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

class CoffeeShopResourceSpecification extends Specification {
    @Ignore('Not implemented yet')
    def 'should return closest coffee shop to Portland Conference Center'() {
        given:
        def mongoClient = new MongoClient()
        def coffeeShop = new CoffeeShopResource(mongoClient)

        when:
        double latitude = 45.5285859
        double longitude = -122.6631354
        def nearestShop = coffeeShop.getNearest(latitude, longitude)

        then:
        nearestShop.name == 'Tiny\'s Coffee'
        println nearestShop
    }

    @Ignore('Not implemented yet')
    def 'should return 404 if no coffee shop found'() {
        given:
        def mongoClient = new MongoClient()
        def coffeeShop = new CoffeeShopResource(mongoClient)

        when:
        double latitude = 37.3981841
        double longitude = -5.9776375999999996
        def nearestShop = coffeeShop.getNearest(latitude, longitude)

        then:
        def exception = thrown(WebApplicationException)
        exception.response.status == Response.Status.NOT_FOUND.statusCode
    }

    //functional test
    def 'should save all fields to the database when order is saved'() {
        given:
        def mongoClient = new MongoClient()
        def database = mongoClient.getDB("Cafelito")
        def collection = database.getCollection('Order')
        collection.drop();

        def coffeeShop = new CoffeeShopResource(mongoClient)

        def drinkType = new DrinkType()
        drinkType.with {
            family = 'coffee'
            name = 'espresso'
        }

        def order = new Order()
        order.with {
            size = 'medium'
            drinker = 'Me'
            type = drinkType
            selectedOptions = ['soy milk']
            coffeeShopId = 89438
        }

        when:
        Response response = coffeeShop.saveOrder(order);

        then:
        collection.count == 1
        def createdOrder = collection.findOne()
        createdOrder['selectedOptions'] == order.selectedOptions
        createdOrder['type'].name == order.type.name
        createdOrder['type'].family == order.type.family
        createdOrder['size'] == order.size
        createdOrder['drinker'] == order.drinker
        createdOrder['coffeeShopId'] == order.coffeeShopId
        createdOrder['_id'] != null
        createdOrder['prettyString'] == null

        cleanup:
        mongoClient.close()
    }

    //functional test
    @Ignore('Not implemented yet')
    def 'should return me an existing order'() {
        given:
        def mongoClient = new MongoClient()
        def database = mongoClient.getDB("Cafelito")

        def coffeeShop = new CoffeeShopResource(database)
        def drinkType = new DrinkType()
        drinkType.with {
            family = 'filter'
            name = 'coffee'
        }
        def expectedOrder = new Order()
        expectedOrder.with {
            size = 'super small'
            drinker = 'Yo'
            type = drinkType
        }

        def coffeeShopId = 89438
        coffeeShop.saveOrder(coffeeShopId, expectedOrder);

        when:
        Order actualOrder = coffeeShop.getOrder(coffeeShopId, expectedOrder.getId());

        then:
        actualOrder != null
        actualOrder == expectedOrder

        cleanup:
        mongoClient.close()
    }

    //functional test
    @Ignore('Not implemented yet')
    def 'should throw a 404 if the order is not found'() {
        given:
        def mongoClient = new MongoClient()
        def database = mongoClient.getDB("Cafelito")
        def coffeeShop = new CoffeeShopResource(database)

        when:
        coffeeShop.getOrder(7474, new ObjectId().toString());

        then:
        def e = thrown(WebApplicationException)
        e.response.status == 404

        cleanup:
        mongoClient.close()
    }

}
