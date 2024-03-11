package org.codecop.rogue.tester.checks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class CheckerTest {

    // JSONObject data = new JSONObject("{\"layout\":\"#####\\n#c@ |\\n#####\\n\"}");

    Findings findings = new Findings();

    @Test
    void shouldHaveLayoutField() {
        String json = "{}";

        new RoomFormatChecker().check(findings, responseOkWith(json));

        Finding expected = Finding.error("JSONObject[\"layout\"] not found.");
        assertEquals(expected, findings.get(0));
    }

    private Response responseOkWith(String json) {
        return new Response(200,
                "application/json", //
                json, //
                new JSONObject(json)); //
    }

}
