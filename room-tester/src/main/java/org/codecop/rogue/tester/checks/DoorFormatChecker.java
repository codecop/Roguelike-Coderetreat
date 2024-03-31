package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

public class DoorFormatChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        if (response.statusCode != 200 && response.statusCode != 404) {
            findings.error("Expect Status Code 200 (or 404), was " + response.statusCode);
        }

        if (response.statusCode == 404) {
            findings.info("Door not found, door is considered open");
            return;
        }

        if ("true".equals(response.body) || "false".equals(response.body)) {
            findings.info("Door found, door is open: " + response.getDoor());
        } else {
            findings.error("Expect true/false for door, was " + response.body);
        }
    }
}
