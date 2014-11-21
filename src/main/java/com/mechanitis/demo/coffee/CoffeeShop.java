package com.mechanitis.demo.coffee;

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

    public static class Point {
        private double[] coordinates;

        public double[] getCoordinates() {
            return coordinates;
        }
    }
}
