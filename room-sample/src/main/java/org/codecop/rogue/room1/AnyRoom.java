package org.codecop.rogue.room1;

import java.util.List;

public interface AnyRoom {

    String display();

    void playerMoves(char direction);

    String description();

    List<Item> getLegend();

}