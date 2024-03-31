package org.codecop.rogue.tester.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MazeTest {

    @Test
    void shouldFindPlayer() {
        Maze maze = new Maze("#####\n# @ |\n#####\n");

        Position pos = maze.getPositionOf('@');

        assertEquals(new Position(2, 1), pos);
    }

    @Test
    void shoudKnowNeighbouringPositions() {
        Maze maze = new Maze("#####\n# @ |\n#####\n");
        Position p = maze.getPositionOf('@');
        assertFalse(maze.isFree(p.up()));
        assertTrue(maze.isFree(p.right()));
        assertFalse(maze.isFree(p.down()));
        assertTrue(maze.isFree(p.left()));
    }
}