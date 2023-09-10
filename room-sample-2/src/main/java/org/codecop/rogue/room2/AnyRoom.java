package org.codecop.rogue.room2;

import java.util.List;
import java.util.Optional;

public interface AnyRoom {

    default String description() {
        return null;
    }

    String layout();

    default List<Item> getLegend() {
        return null;
    }

    Optional<String> movesTo(Position newPosition);

    default boolean canExitDoor() {
        return true;
    }

    default Optional<String> interactWith(@SuppressWarnings("unused") Item item) {
        return Optional.empty();
    }

}
