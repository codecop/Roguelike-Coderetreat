package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;
import org.json.JSONException;

/**
 * Checks the technical format of a room request
 * <ul>
 *     <li>A JSON with response code 200.</li>
 *     <li>Only allowed JSON elements.</li>
 *     <li>layout is mandatory.</li>
 * </ul>
 */
public class RoomFormatChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        Check.statusCodeIs(findings, response, 200);

        Check.contentTypeIsJson(findings, response);

        if (response.jsonBody == null) {
            throw new JSONException("Expect valid JSON {...}, was " + response.body);
        }

        Check.jsonBodyOnlyHas(findings, response, "layout", "description", "legend");

        response.getLayout();
    }

}
