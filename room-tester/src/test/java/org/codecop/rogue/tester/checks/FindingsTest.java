package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FindingsTest {

    Findings f = new Findings();

    @Test
    void shouldCountByLevel() {
        assertEquals(0, f.count(Level.ERROR));
    }

    @Test
    void shouldCountErrors() {
        f.error("Foo");
        assertEquals(1, f.count(Level.ERROR));
        assertEquals(0, f.count(Level.WARNING));
        assertTrue(f.hasErrors());
    }

}