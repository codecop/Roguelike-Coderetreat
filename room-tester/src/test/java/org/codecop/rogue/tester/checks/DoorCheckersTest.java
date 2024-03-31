package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.tester.model.Response;
import org.junit.jupiter.api.Test;

class DoorCheckersTest {

    Findings findings = new Findings();

    @Test
    void shouldBeOpenOn404() {
        Checkers.doorCheckers().check(findings, responseNotFound());

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldHaveClosedDoor() {
        Checkers.doorCheckers().check(findings, responseOkWith("false"));

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldHaveOpenDoor() {
        Checkers.doorCheckers().check(findings, responseOkWith("true"));

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldHaveTrueOrFalse() {
        Checkers.doorCheckers().check(findings, responseOkWith("open"));

        Finding expected = Finding.error("Expect true/false for door, was open");
        assertEquals(expected, findings.get(0));
    }

    private Response responseOkWith(String json) {
        return new Response(200, null, json);
    }

    private Response responseNotFound() {
        return new Response(404, null, null);
    }

}
