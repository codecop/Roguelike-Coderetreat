package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Checks the technical format of a walk request
 * <ul>
 *     <li>A JSON with response code 201.</li>
 *     <li>Only allowed JSON elements.</li>
 *     <li>optional message.</li>
 * </ul>
 */
public class MessageFormatChecker implements Checker {

    private final Set<String> allowedJsonKeys = new HashSet<>();
    {
        allowedJsonKeys.add("message");
    }

    @Override
    public void check(Findings findings, Response response) {
        if (response.statusCode != 200 && response.statusCode != 201 && response.statusCode != 202) {
            findings.error("Expect Status Code 2xx, was " + response.statusCode);
        } else if (response.statusCode != 201) {
            findings.warn("Expect Status Code 201, was " + response.statusCode);
        }

        if (!response.hasJsonContentType()) {
            findings.warn("Expect ContentType '" + Response.CONTENT_TYPE + "', was " + response.contentType);
        }

        if (response.jsonBody != null) {
            JSONObject json = response.jsonBody;
            for (String key : json.keySet()) {
                if (!allowedJsonKeys.contains(key)) {
                    findings.warn("Unexpected key in JSON " + key);
                }
            }
        }
    }

}
