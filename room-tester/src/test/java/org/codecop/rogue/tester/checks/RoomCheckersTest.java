package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.tester.model.Response;
import org.junit.jupiter.api.Test;

class RoomCheckersTest {

    Findings findings = new Findings();

    @Test
    void shouldHaveNoFindings() {
        String json = "{\"description\" : \"A locked room. There is a <c>hest at the South wall.\",\n" +
                "\"layout\" : \"#######\\n#  @  #\\n#     #\\n#     |\\n#     #\\n#  c  #\\n#######\\n\",\n" +
                "\"legend\":[{\"item\":\"c\",\"description\":\"a sturdy chest\"}]}";

        Checkers.roomCheckers().check(findings, responseOkWith(json));

        findings.forEach(finding -> assertEquals(Level.INFO, finding.level));
    }

    @Test
    void shouldIntentRoomLayout() {
        String json = "{\"description\" : \"A locked room. There is a <c>hest at the South wall.\",\n" +
                "\"layout\" : \"#######\\n#  @  #\\n#     #\\n#     |\\n#     #\\n#  c  #\\n#######\\n\",\n" +
                "\"legend\":[{\"item\":\"c\",\"description\":\"a sturdy chest\"}]}";

        Checkers.roomCheckers().check(findings, responseOkWith(json));

        assertEquals("INFO\tLayout found: #######\n" +
                "    \t              #  @  #\n" +
                "    \t              #     #\n" +
                "    \t              #     |\n" +
                "    \t              #     #\n" +
                "    \t              #  c  #\n" +
                "    \t              #######\n", findings.get(0).toString());
    }

    @Test
    void shouldHaveLayoutField() {
        String json = "{}";

        // new RoomFormatChecker().check(findings, responseOkWith(json));
        // use all checkers to see early return
        Checkers.roomCheckers().check(findings, responseOkWith(json));

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

    @Test
    void shouldHaveBetterDescription() {
        String json = "{\"layout\":\"#####\\n# @ |\\n#####\\n\"," +
                "\"description\" : \"Foo.\"}";

        new RoomDescriptionChecker().check(findings, responseOkWith(json));

        Finding expected = Finding.warn("Expect useful description, was short Foo.");
        assertEquals(expected, findings.get(1));
    }

    @Test
    void shouldHaveDescriptionOfItem() {
        String json = "{\"layout\":\"" +
                "#####\\n" +
                "#c@ |\\n" +
                "#####\\n\"}";

        new RoomItemChecker().check(findings, responseOkWith(json));

        Finding expected = Finding.warn("Expect description with mnemonics <x> for items or monsters [c]");
        assertEquals(expected, findings.get(1));
    }

    private Response responseOkWith(String json) {
        return new Response(200,
                "application/json", //
                json //
        );
    }

}
