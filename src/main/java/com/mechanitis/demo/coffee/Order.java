package com.mechanitis.demo.coffee;

import org.mongodb.morphia.annotations.Id;

public class Order {
    @Id
    private String id;
    private String drinker;
    private String size;
    private String[] selectedOptions;
    private DrinkType type;
    private long coffeeShopId;

    public String getDrinker() {
        return drinker;
    }

    public String getSize() {
        return size;
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public DrinkType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setCoffeeShopId(final long coffeeShopId) {
        this.coffeeShopId = coffeeShopId;
    }

    public long getCoffeeShopId() {
        return coffeeShopId;
    }
}
