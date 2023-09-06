package org.codecop.rogue.room2;

import java.util.List;
import java.util.Optional;

public interface AnyRoom {

    String description();

    String layout();

    List<Item> getLegend();

    Optional<String> movesTo(Position newPosition);

    boolean canExitDoor();

    Optional<String> interactWith(Item item);

}
