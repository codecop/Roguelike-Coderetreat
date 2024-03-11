package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

public class RoomDescriptionChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        JSONObject json = response.jsonBody;
        // String layout = json.getString("layout");

        if (json.has("description")) {
            String description = json.getString("description");
            findings.info("Description found: " + description);

            if (description.length() < 10) {
                findings.warn("Expect useful description, was short " + description);
            }

            char firstLetter = description.charAt(0);
            if (!Character.isUpperCase(firstLetter)) {
                findings.warn("Start description with upper case, was " + firstLetter);
            }

            char lastLetter = description.charAt(description.length() - 1);
            if (lastLetter != '.' && lastLetter != '!') {
                findings.warn("End description with . or !, was " + lastLetter);
            }

            if (description.indexOf('\n') != -1) {
                findings.warn("Description should not have newline, was " + description);
            }
        }
    }

}
