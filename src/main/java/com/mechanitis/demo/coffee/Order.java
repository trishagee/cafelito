package com.mechanitis.demo.coffee;

import org.mongodb.morphia.annotations.Id;

public class Order {
//    {
//        "type": {
//        "name": "Tea",
//                "family": "that other drink"
//    },
//        "size": "Large",
//            "drinker": "Trisha"
//    }
//


    @Id
    private String id;
    private String drinker;
    private String size;
    private long coffeeShopId;
    private String[] selectedOptions;

    public DrinkType getType() {
        return type;
    }

    public long getCoffeeShopId() {
        return coffeeShopId;
    }

    public String getSize() {
        return size;
    }

    public String getDrinker() {
        return drinker;
    }

    private DrinkType type;

    public String getId() {
        return id;
    }
}
