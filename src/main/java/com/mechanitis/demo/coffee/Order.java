package com.mechanitis.demo.coffee;

import org.mongojack.Id;
import org.mongojack.ObjectId;

public class Order {
//    {
//        "type": {
//             "name": "Cappuccino",
//             "family": "Coffee"
//    },
//        "size": "Medium",
//        "drinker": "Trisha"
//    }

    @ObjectId
    @Id
    private String id;
    private long coffeeShopId;

    private String drinker;
    private String size;
    private DrinkType type;
    private String[] selectedOptions;

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

    public DrinkType getType() {
        return type;
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public void setCoffeeShopId(final long coffeeShopId) {
        this.coffeeShopId = coffeeShopId;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
