package org.codecop.rogue.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WallTest {

    @Test
    void shouldPrint() {
        Wall wall = new Wall();

        String map = wall.toString();

        assertEquals(map, "#");
    }
}
