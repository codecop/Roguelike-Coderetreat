package org.codecop.rogue.room2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class ExtendedRoomTest {

    @Test
    void hasDescription() {
        AnyRoom room = new ExtendedRoom();

        String display = room.layout();
        assertEquals("" + //
                "#######\n" + //
                "#  @  #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#  c  #\n" + //
                "#######\n", display);

        String description = room.description();
        assertEquals("A locked room. There is a <c>hest at the South wall.", description);
    }

    @Test
    void closedDoor() {
        AnyRoom room = new ExtendedRoom();

        assertFalse(room.canExitDoor());
    }

    @Test
    void searchChest() {
        AnyRoom room = new ExtendedRoom();

        Optional<String> message = room.interactWith(new Item('c', null));
        assertEquals("There is a key in the chest.", message.get());

        String display = room.layout();
        assertEquals("" + //
                "#######\n" + //
                "#  @  #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#  k  #\n" + //
                "#######\n", display);

        String description = room.description();
        assertEquals("A locked room. You found the <k>ey.", description);
    }

    @Test
    void pickupKey() {
        AnyRoom room = new ExtendedRoom();
        room.interactWith(new Item('c', null));

        room.interactWith(new Item('k', null));
        assertTrue(room.canExitDoor());

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

}
