package com.mechanitis.demo.coffee;

public class DrinkType {
//        "type": {
//        "name": "Cappuccino",
//                "family": "Coffee"
//    }
    private String name;
    private String family;

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    @Override
    public String toString() {
        return "DrinkType{"
               + "type='" + name + '\''
               + ", family='" + family + '\''
               + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrinkType drinkType = (DrinkType) o;

        if (!family.equals(drinkType.family)) {
            return false;
        }
        if (!name.equals(drinkType.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + family.hashCode();
        return result;
    }
}
