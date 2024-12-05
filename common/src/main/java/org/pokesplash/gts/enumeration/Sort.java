package org.pokesplash.gts.enumeration;

import org.pokesplash.gts.UI.FilterType;

public enum Sort {
    DATE("Date"),
    NAME("Name"),
    PRICE("Price"),
    NONE("None");

    public final String name;

    Sort(String name) {
        this.name = name;
    }

    public Sort getNext() {
        return values()[(ordinal() + 1) % values().length];
    }
}
