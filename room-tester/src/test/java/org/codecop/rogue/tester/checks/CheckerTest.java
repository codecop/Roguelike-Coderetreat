package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class CheckerTest {

    Findings findings = new Findings();

    @Test
    void shouldHaveNoFindings() {
        String json = "{\"description\" : \"A locked room. There is a <c>hest at the South wall.\",\n" +
                "\"layout\" : \"#######\\n#  @  #\\n#     #\\n#     |\\n#     #\\n#  c  #\\n#######\\n\",\n" +
                "\"legend\":[{\"item\":\"c\",\"description\":\"a sturdy chest\"}]}";

        findings = new Checkers().check(responseOkWith(json));

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldHaveLayoutField() {
        String json = "{}";

        // new RoomFormatChecker().check(findings, responseOkWith(json));
        findings = new Checkers().check(responseOkWith(json));

        Finding expected = Finding.error("JSONObject[\"layout\"] not found.");
        assertEquals(expected, findings.get(0));
    }

    @Test
    void shouldHaveWallsAround() {
        String json = "{\"layout\":\"" +
                "## ##\\n" +
                "#c@ |\\n" +
                "#####\\n\"}";

        new RoomLayoutChecker().check(findings, responseOkWith(json));

        Finding expected = Finding.error("Expected walls around level");
        // 0 is layout found
        assertEquals(expected, findings.get(1));
    }

    @Test
    void shouldHaveWayToDoor() {
        String json = "{\"layout\":\"" +
                "#####\\n" +
                "#@ #|\\n" +
                "#####\\n\"}";

        new RoomLayoutChecker().check(findings, responseOkWith(json));

        Finding expected = Finding.warn("Expected free way to door");
        assertEquals(expected, findings.get(1));
    }

    private Response responseOkWith(String json) {
        return new Response(200,
                "application/json", //
                json, //
                new JSONObject(json)); //
    }

}
