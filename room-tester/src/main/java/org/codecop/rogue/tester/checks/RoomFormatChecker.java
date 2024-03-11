package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RoomFormatChecker implements Checker {

    @Override
    public void check(List<Finding> findings, Response response) {
        try {

            if (response.statusCode != 200) {
                findings.add(Finding.error("Status code expected 200, was " + response.statusCode));
            }

            if (!response.contentType.startsWith("application/json")) {
                findings.add(Finding.warn("ContentType expected 'application/json', was " + response.contentType));
            }

            if (response.jsonBody == null) {
                findings.add(Finding.error("JSON expected, was " + response.body));
            }
            JSONObject json = response.jsonBody;

            json.getString("layout");

        } catch (JSONException e) {
            findings.add(Finding.error(e.getMessage()));
        }
    }
}
