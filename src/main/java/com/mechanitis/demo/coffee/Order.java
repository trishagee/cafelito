package com.mechanitis.demo.coffee;

import org.mongodb.morphia.annotations.Id;

public class Order {
//    {
//        "type": {
//        "name": "Latte",
//                "family": "coffee"
//    },
//        "size": "Medium",
//            "drinker": "Trisha"
//    }

    private String drinker;
    private String size;
    private long coffeeShopId;
    private DrinkType type;

    @Id
    private String id;

    public String getDrinker() {
        return drinker;
    }

    public String getSize() {
        return size;
    }

    public long getCoffeeShopId() {
        return coffeeShopId;
    }

    public DrinkType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
