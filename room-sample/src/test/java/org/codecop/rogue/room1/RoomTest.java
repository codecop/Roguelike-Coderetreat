package org.codecop.rogue.room1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoomTest {

    @Test
    void roomHasWalls() {
        Room room = new Room();

        String display = room.display();

        assertEquals("" + //
                "#######\n" + //
                "#  @  #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#######\n", display);
    }

}
