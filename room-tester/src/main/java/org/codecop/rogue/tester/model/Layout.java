package org.codecop.rogue.tester.model;

import java.util.List;
import java.util.stream.Collectors;

public class Layout {
    private final String s;

    public Layout(String s) {

        this.s = s;
    }

    public List<Item> itemsOrMonsters() {
        String itemsOrMonsters = s.replaceAll("[# @|\n]", "");
        return itemsOrMonsters.chars().
                mapToObj(c -> new Item((char) c)).
                collect(Collectors.toList());
    }
}
