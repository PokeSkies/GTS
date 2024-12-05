package org.pokesplash.gts.UI;

public enum FilterType {
    ALL("All"),
    POKEMON("Pokemon"),
    ITEMS("Items");

    public final String name;

    FilterType(String name) {
        this.name = name;
    }

    public FilterType getNext() {
        return values()[(ordinal() + 1) % values().length];
    }
}
