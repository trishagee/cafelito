package com.mechanitis.demo.coffee;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class CoffeeShopService extends Service<CoffeeShopConfiguration> {
    public static void main(String[] args) throws Exception {
        new CoffeeShopService().run(args);
    }
    @Override
    public void initialize(final Bootstrap<CoffeeShopConfiguration> bootstrap) {
        bootstrap.setName("coffee-app");
        AssetsBundle bundle = new AssetsBundle("/html", "/");
        bootstrap.addBundle(bundle);
    }

    @Override
    public void run(final CoffeeShopConfiguration configuration, final Environment environment) throws Exception {
        MongoClient mongoClient = new MongoClient();
        DB database = mongoClient.getDB("TrishaCoffee");
        environment.addResource(new CoffeeShopResource(database));
        environment.manage(new MongoClientManager(mongoClient));
    }
}
