package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Checks the technical format of a room request
 * <ul>
 *     <li>A JSON with response code 200.</li>
 *     <li>Only allowed JSON elements.</li>
 *     <li>layout is mandatory.</li>
 * </ul>
 */
public class RoomFormatChecker implements Checker {

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

        if (!response.hasJsonContentType()) {
            findings.warn("Expect ContentType '" + Response.CONTENT_TYPE + "', was " + response.contentType);
        }

        if (response.jsonBody == null) {
            throw new JSONException("Expect valid JSON {...}, was " + response.body);
        }

        JSONObject json = response.jsonBody;
        for (String key : json.keySet()) {
            if (!allowedJsonKeys.contains(key)) {
                findings.warn("Unexpected key in JSON: " + key);
            }
        }

        response.getLayout();
    }

}
