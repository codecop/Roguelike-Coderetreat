package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

import java.util.Arrays;

/**
 * Checks the technical format of a walk request
 * <ul>
 *     <li>A JSON with response code 201.</li>
 *     <li>Only allowed JSON element message.</li>
 *     <li>optional message.</li>
 * </ul>
 */
public class MessageFormatChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        if (Check.statusCodeIs(findings, response, 200, 201, 202)) {

        } else if (response.statusCode != 201) {
            findings.warn("Expect Status Code 201, was " + response.statusCode);
        }

        Check.contentTypeIsJson(findings, response);

        Check.jsonBodyOnlyHas(findings, response, "message");
    }

}
