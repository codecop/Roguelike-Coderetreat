package org.codecop.rogue.tester.model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item that = (Item) o;
        return this.item == that.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }

    @Override
    public String toString() {
        return Character.toString(item);
    }

    public String getMnemonic() {
        return "<" + this.item + ">";
    }
}
