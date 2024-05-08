package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

/**
 * Checks the technical format of a interaction request. It is optional.
 */
public class InteractionMessageFormatChecker extends WalkMessageFormatChecker {

    @Override
    public void check(Findings findings, Response response) {
        if (response.statusCode == 404) {
            findings.info("Interaction not found, nothing happens");
            return;
        }

        super.check(findings, response);
    }

}
