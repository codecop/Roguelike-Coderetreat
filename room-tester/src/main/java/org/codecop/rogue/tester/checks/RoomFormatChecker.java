package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

public class RoomFormatChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        if (response.statusCode != 200) {
            findings.error("Expect Status Code 200, was " + response.statusCode);
        }

        if (response.contentType == null || !response.contentType.startsWith("application/json")) {
            findings.warn("Expect ContentType 'application/json', was " + response.contentType);
        }

        if (response.jsonBody == null) {
            findings.error("Expect valid JSON {...}, was " + response.body);
            return;
        }

        JSONObject json = response.jsonBody;
        json.getString("layout");
    }

}
