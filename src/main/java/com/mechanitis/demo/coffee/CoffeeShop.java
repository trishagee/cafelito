package com.mechanitis.demo.coffee;

import org.mongodb.morphia.geo.Point;

public class CoffeeShop {
    private Point location;
    private String name;
    private long openStreetMapId;

    public String getName() {
        return name;
    }

    public Point getLocation() {
        return location;
    }

    public long getOpenStreetMapId() {
        return openStreetMapId;
    }

}
