package org.codecop.rogue.tester.model;

public class Item {
    public final char item;
    public final String description;

    public Item(char item) {
        this(item, null);
    }

    public Item(char item, String description) {
        this.item = item;
        this.description = description;
    }

    @Override
    public String toString() {
        return Character.toString(item);
    }

    public String getMnemonic() {
        return "<" + this.item + ">";
    }
}
