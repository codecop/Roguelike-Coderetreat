package org.codecop.rogue.tester.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MazeTest {

    @Test
    void shouldFindPlayer() {
        Maze maze = new Maze("#####\n# @ |\n#####\n");

        Position pos = maze.getPositionOf('@');

        assertEquals(new Position(2, 1), pos);
    }


}