package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CoffeeShopApplication extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new CoffeeShopApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/html/", "/coffeeshop/"));
    }

    @Override
    public void run(Configuration configuration, Environment environment)
            throws Exception {
        environment.jersey().register(new CoffeeShopResource(new MongoClient()));
        environment.jersey().setUrlPattern("/service/*");
    }
}
