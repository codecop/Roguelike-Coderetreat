package org.codecop.rogue.room1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoomTest {

    @Test
    void hasWalls() {
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

    @Test
    void playerMovesRight() {
        Room room = new Room();
        
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
        Room room = new Room();

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
        Room room = new Room();

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
    
}
