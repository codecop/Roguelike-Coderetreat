package org.codecop.rogue.room1;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Item {

    private String item;
    private String description;

    public Item() {
    }

    public Item(char item, String description) {
        this.item = "" + item;
        this.description = description;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
