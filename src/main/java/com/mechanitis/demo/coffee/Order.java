package com.mechanitis.demo.coffee;

import org.mongojack.Id;
import org.mongojack.ObjectId;

public class Order {
    @ObjectId
    @Id
    private String id;
    
    private long coffeeShopId;
    private String drinker;
    private String size;
    private String[] selectedOptions;
    private DrinkType type;

    public String getId() {
        return id;
    }

    public long getCoffeeShopId() {
        return coffeeShopId;
    }

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

    public void setCoffeeShopId(final long coffeeShopId) {
        this.coffeeShopId = coffeeShopId;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
