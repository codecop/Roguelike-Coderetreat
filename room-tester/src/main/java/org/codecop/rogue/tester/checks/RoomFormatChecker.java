package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomFormatChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        try {

            if (response.statusCode != 200) {
                findings.error("Status code expected 200, was " + response.statusCode);
            }

            if (response.contentType == null || !response.contentType.startsWith("application/json")) {
                findings.warn("ContentType expected 'application/json', was " + response.contentType);
            }

            if (response.jsonBody == null) {
                findings.error("JSON expected, was " + response.body);
                return;
            }
            JSONObject json = response.jsonBody;

            json.getString("layout");

        } catch (JSONException e) {
            findings.error(e);
        }
    }
}
