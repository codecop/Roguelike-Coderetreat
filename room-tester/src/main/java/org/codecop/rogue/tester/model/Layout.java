package org.codecop.rogue.tester.model;

import java.util.List;
import java.util.stream.Collectors;

public class Layout {
    private final String layout;

    public Layout(String layout) {
        this.layout = layout;
    }

    public List<Item> itemsOrMonsters() {
        String itemsOrMonsters = layout.replaceAll("[# @|\n]", "");
        return itemsOrMonsters.chars().
                mapToObj(c -> new Item((char) c)).
                collect(Collectors.toList());
    }

    public int count(char c) {
        return Strings.count(layout, c);
    }

    public Maze toMaze() {
        return new Maze(layout);
    }

    @Override
    public String toString() {
        return layout;
    }
}
