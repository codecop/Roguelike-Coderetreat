package org.codecop.rogue.room1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoomTest {

    @Test
    void hasWalls() {
        AnyRoom room = new Room();

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

    @Test
    void playerMovesRight() {
        AnyRoom room = new Room();

        room.playerMoves('d');

        String display = room.display();
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
    void playerMovesDown() {
        AnyRoom room = new Room();

        room.playerMoves('s');

        String display = room.display();
        assertEquals("" + //
                "#######\n" + //
                "#     #\n" + //
                "#  @  #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#######\n", display);
    }

    @Test
    void playerDoesNotMoveUpOnWall() {
        AnyRoom room = new Room();

        room.playerMoves('w');

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

    @Test
    void playerExitsOpenRoom() {
        Room room = new Room();
        room.setDoorOpen(true);

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
                "#     @\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#######\n", display);
    }

    // NOTE: ^^^ minimal functionality, 5 tests and 40' for me = 2 sessions for people.
    
    @Test
    void playerCanNotExitClosedDoor() {
        Room room = new Room();
        room.setDoorOpen(false);

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
                "#     #\n" + //
                "#######\n", display);
    }

}
