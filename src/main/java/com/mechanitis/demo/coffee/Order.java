package com.mechanitis.demo.coffee;

import org.mongodb.morphia.annotations.Id;

public class Order {
//    {
//        "type": {
//        "name": "Cappuccino",
//                "family": "coffee"
//    },
//        "size": "Small",
//            "drinker": "Trisha"
//    }
    private String drinker;
    private String size;
    private long coffeeShopId;
    private DrinkType type;
    private String[] selectedOptions;

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

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }
}
