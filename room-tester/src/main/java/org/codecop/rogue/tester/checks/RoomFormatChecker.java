package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class RoomFormatChecker implements Checker {

    private static final String CONTENT_TYPE = "application/json";

    private final Set<String> allowedJsonKeys = new HashSet<>();

    {
        allowedJsonKeys.add("layout");
        allowedJsonKeys.add("description");
        allowedJsonKeys.add("legend");
    }

    @Override
    public void check(Findings findings, Response response) {
        if (response.statusCode != 200) {
            findings.error("Expect Status Code 200, was " + response.statusCode);
        }

        if (response.contentType == null || !response.contentType.startsWith(CONTENT_TYPE)) {
            findings.warn("Expect ContentType '" + CONTENT_TYPE + "', was " + response.contentType);
        }

        if (response.jsonBody == null) {
            findings.error("Expect valid JSON {...}, was " + response.body);
            return;
        }

        JSONObject json = response.jsonBody;
        for (String key : json.keySet()) {
            if (!allowedJsonKeys.contains(key)) {
                findings.warn("Unexpected key in JSON " + key);
            }
        }

        json.getString("layout");
    }

}
