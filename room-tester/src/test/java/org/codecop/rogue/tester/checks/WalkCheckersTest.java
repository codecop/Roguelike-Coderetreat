package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.tester.model.Response;
import org.junit.jupiter.api.Test;

class WalkCheckersTest {

    Findings findings = new Findings();

    @Test
    void shouldHaveNoFindingsEmptyJson() {
        String json = "{}";

        Checkers.walkCheckers().check(findings, responseCreatedWith(json));

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldHaveNoFindingsNoJson() {

        Checkers.walkCheckers().check(findings, responseCreatedWith(null));

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldHaveBetterMessage() {
        String json = "{\"message\" : \"Foo.\"}";

        new MessageTextChecker().check(findings, responseCreatedWith(json));

        Finding expected = Finding.warn("Expect useful message, was short Foo.");
        assertEquals(expected, findings.get(1));
    }

    private Response responseCreatedWith(String json) {
        return new Response(201,
                "application/json", //
                json //
        );
    }

}
