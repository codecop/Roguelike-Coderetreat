package org.codecop.rogue.room1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExtendedRoomTest {

    @Test
    void playerCanNotExitClosedDoor() {
        AnyRoom room = new ExtendedRoom();

        room.playerMoves('s');
        room.playerMoves('s');
        room.playerMoves('d');
        room.playerMoves('d');
        room.playerMoves('d');

        String display = room.display();
        assertEquals("" + //
                "#######\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#    @|\n" + //
                "#     #\n" + //
                "#  c  #\n" + //
                "#######\n", display);

        String description = room.description();
        assertEquals("A locked room. There is a <c>hest at the South wall.", description);
    }

    @Test
    void searchChest() {
        AnyRoom room = new ExtendedRoom();

        room.playerMoves('s');
        room.playerMoves('s');
        room.playerMoves('s');
        room.playerMoves(' ');

        String display = room.display();
        assertEquals("" + //
                "#######\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#  @  #\n" + //
                "#  k  #\n" + //
                "#######\n", display);

        String description = room.description();
        assertEquals("A locked room. You found the <k>ey.", description);
    }

    @Test
    void playerCanNotSearchChestWhenAway() {
        AnyRoom room = new ExtendedRoom();

        room.playerMoves(' ');

        String display = room.display();
        assertEquals("" + //
                "#######\n" + //
                "#  @  #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#  c  #\n" + //
                "#######\n", display);
    }

    @Test
    void pickupKey() {
        AnyRoom room = new ExtendedRoom();

        room.playerMoves('s');
        room.playerMoves('s');
        room.playerMoves('s');
        room.playerMoves(' ');
        room.playerMoves(' ');

        String display = room.display();
        assertEquals("" + //
                "#######\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#  @  #\n" + //
                "#     #\n" + //
                "#######\n", display);
    }
    
    // NOTE: ^^^ chest/item functionality, 4+ tests and 45' for me = 2 sessions for people. that is max.
    
}
