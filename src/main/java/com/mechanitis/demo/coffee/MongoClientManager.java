package com.mechanitis.demo.coffee;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.lifecycle.Managed;
import org.eclipse.jetty.util.component.LifeCycle;

public class MongoClientManager implements Managed {
    private final MongoClient mongoClient;

    public MongoClientManager(final MongoClient mongoClient) {
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
