package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

public class MongoClientManager implements Managed{
    private MongoClient mongoClient;

    public MongoClientManager(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}
