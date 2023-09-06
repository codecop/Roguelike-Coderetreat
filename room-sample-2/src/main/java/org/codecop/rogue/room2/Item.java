package org.codecop.rogue.room2;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Item {

    private char item;
    private String description;

    public Item() {
    }

    public Item(char item, String description) {
        this.item = item;
        this.description = description;
    }

    public char getItem() {
        return item;
    }

    public void setItem(char item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
