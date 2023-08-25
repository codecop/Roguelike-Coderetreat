package org.codecop.rogue.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HelloTest {

    Hello hello = new Hello();

    @Test
    void get() {
        assertEquals("World!", hello.getName());
    }

}
