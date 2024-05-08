package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FindingsTest {

    Findings findings = new Findings();

    @Test
    void shouldCountByLevel() {
        assertEquals(0, findings.count(Level.ERROR));
    }

    @Test
    void shouldCountFatalEntries() {
        findings.fatal("Foo");
        assertEquals(1, findings.count(Level.FATAL));
        assertEquals(0, findings.count(Level.ERROR));
        assertEquals(0, findings.count(Level.WARNING));
        assertTrue(findings.isFatal());
    }

    @Test
    void shouldNotRepeatDuplicateElements() {
        findings.error("Foo");
        findings.error("Foo");
        findings.warn("Foo");
        assertEquals(1, findings.count(Level.ERROR));
    }

}