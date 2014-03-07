package com.mechanitis.demo

import com.mechanitis.demo.coffee.CoffeeShopResource
import com.mechanitis.demo.coffee.DrinkType
import com.mechanitis.demo.coffee.Order
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.MongoClient
import org.bson.types.ObjectId
import spock.lang.Specification

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

class CoffeeShopResourceSpecification extends Specification {
    def 'should return a dummy shop for testing'() {
        given:
        def coffeeShop = new CoffeeShopResource(null)

        when:
        def nearestShop = coffeeShop.getDummy()

        then:
        nearestShop.name == 'A dummy coffee shop'
    }

    def 'should return Cafe Nero as the closest coffee shop to Westminster Abbey'() {
        given:
        def mongoClient = new MongoClient()
        def coffeeShop = new CoffeeShopResource(mongoClient.getDB("TrishaCoffee"))

        when:
        double latitude = 51.4994678
        double longitude = -0.128888
        def nearestShop = coffeeShop.getNearest(latitude, longitude)

        then:
        nearestShop.name == 'Caffè Nero'
        println nearestShop.allValues
    }

    def 'should return Costa as the closest coffee shop to Earls Court Road'() {
        given:
        def mongoClient = new MongoClient()
        def coffeeShop = new CoffeeShopResource(mongoClient.getDB("TrishaCoffee"))

        when:
        double latitude = 51.4950233
        double longitude = -0.1962431
        def nearestShop = coffeeShop.getNearest(latitude, longitude)

        then:
        nearestShop.name == 'Costa'
        println nearestShop.allValues
    }

    def 'should return null if no coffee shop found'() {
        given:
        def mongoClient = new MongoClient()
        def coffeeShop = new CoffeeShopResource(mongoClient.getDB("TrishaCoffee"))

        when:
        double latitude = 37.3981841
        double longitude = -5.9776375999999996
        def nearestShop = coffeeShop.getNearest(latitude, longitude)

        then:
        nearestShop == null
    }

    def 'should give me back the order ID when an order is successfully created'() {
        given:
        DB database = Mock()
        database.getCollection(_) >> { Mock(DBCollection) }

        def coffeeShop = new CoffeeShopResource(database)
        def order = new Order()

        //set ID for testing
        def orderId = new ObjectId()
        order.setId(orderId.toString())

        when:
        Response response = coffeeShop.saveOrder(75847854, order);

        then:
        response != null
        response.status == Response.Status.CREATED.statusCode
        response.headers['Location'][0].toString() == orderId.toString()
    }

    //functional test
    def 'should save all fields to the database when order is saved'() {
        given:
        def mongoClient = new MongoClient()
        def database = mongoClient.getDB("TrishaCoffee")
        def collection = database.getCollection('orders')
        collection.drop();

        def coffeeShop = new CoffeeShopResource(database)

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
        }

        def coffeeShopId = 89438

        when:
        Response response = coffeeShop.saveOrder(coffeeShopId, order);

        then:
        collection.count == 1
        def createdOrder = collection.findOne()
        println createdOrder
        createdOrder['selectedOptions'] == order.selectedOptions
        createdOrder['type'].name == order.type.name
        createdOrder['type'].family == order.type.family
        createdOrder['size'] == order.size
        createdOrder['drinker'] == order.drinker
        createdOrder['coffeeShopId'] == order.coffeeShopId
        createdOrder['_id'] != null
        createdOrder['prettyString'] == null
        println createdOrder
        //    form = {
        //        "selectedOptions": [],
        //        "type": {
        //            "name": "Cappuccino",
        //            "family": "Coffee"
        //        },
        //        "size": "Small",
        //        "drinker": "Trisha"
        //    }

        cleanup:
        mongoClient.close()
    }

    //functional test
    def 'should return me an existing order'() {
        given:
        def mongoClient = new MongoClient()
        def database = mongoClient.getDB("TrishaCoffee")

        def coffeeShop = new CoffeeShopResource(database)
        def expectedOrder = new Order([] as String[], new DrinkType('filter', 'coffee'), 'super small', 'Yo')

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
    def 'should throw a 404 if the order is not found'() {
        given:
        def mongoClient = new MongoClient()
        def database = mongoClient.getDB("TrishaCoffee")
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
