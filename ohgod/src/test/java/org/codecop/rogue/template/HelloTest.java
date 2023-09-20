package org.codecop.rogue.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.template.Hello;
import org.junit.jupiter.api.Test;

class HelloTest {

    Hello hello = new Hello();

    @Test
    void get() {
        assertEquals("World!", hello.getName());
    }

}
