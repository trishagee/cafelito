package com.mechanitis.demo.coffee;

public class DrinkType {
    private String name;
    private String family;

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
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

        if (family != null ? !family.equals(drinkType.family) : drinkType.family != null) {
            return false;
        }
        if (name != null ? !name.equals(drinkType.name) : drinkType.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (family != null ? family.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DrinkType{"
               + "name='" + name + '\''
               + ", family='" + family + '\''
               + '}';
    }
}
