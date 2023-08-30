package org.codecop.rogue.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {

    private final Map<Character, Item> items = new HashMap<>();

    public void putItem(char item, String description) {
        putItem(new Item(item, description));
    }

    public void putItem(Item item) {
        items.put(item.item, item);
    }

    public Item getItem(char key) {
        return items.get(key);
    }

    public void removeItem(char item) {
        items.remove(item);
    }

    public List<Item> list() {
        return new ArrayList<>(items.values());
    }

    public void reset() {
        items.clear();
    }

}
