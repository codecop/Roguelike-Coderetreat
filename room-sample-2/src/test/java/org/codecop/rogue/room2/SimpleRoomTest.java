package org.codecop.rogue.room2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.codecop.rogue.room2.AnyRoom;
import org.codecop.rogue.room2.SimpleRoom;
import org.junit.jupiter.api.Test;

class SimpleRoomTest {

    @Test
    void showsLayout() {
        AnyRoom room = new SimpleRoom();

        String display = room.layout();

        assertEquals("" + //
                "#######\n" + //
                "#  @  #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#######\n", display);
    }

    @Test
    void movesPlayer() {
        AnyRoom room = new SimpleRoom();

        room.movesTo(new Position(1, 4));

        String display = room.layout();
        assertEquals("" + //
                "#######\n" + //
                "#   @ #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#######\n", display);
    }

    @Test
    void doorIsOpen() {
        AnyRoom room = new SimpleRoom();

        assertTrue(room.canExitDoor());
    }

}
