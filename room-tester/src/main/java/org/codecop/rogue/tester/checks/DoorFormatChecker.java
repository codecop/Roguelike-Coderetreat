package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

/**
 * Checks the technical format of a door request
 * <ul>
 *     <li>A JSON with response code 200 or 404.</li>
 *     <li>Only allowed true or false.</li>
 * </ul>
 */
public class DoorFormatChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        if (response.statusCode == 404) {
            findings.info("Door not found, door is considered open");
            return;
        }

        Check.statusCodeIs(findings, response, 200);

        if ("true".equals(response.body) || "false".equals(response.body)) {
            findings.infoFound("Door", (response.getDoor() ? "open" : "closed"));
        } else {
            findings.error("Expect true/false for door, was " + response.body);
        }
    }

}
