package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

import java.util.Optional;

public class RoomDescriptionChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        Optional<String> optionalDescription = response.getDescription();
        if (optionalDescription.isEmpty()) {
            findings.infoOptional("room description");
            return;
        }

        String description = optionalDescription.get();
        findings.infoFound("Room description", description);

        if (description.length() < 10) {
            findings.warn("Expect useful description, was short " + description);
            if (description.isEmpty()) {
                return;
            }
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
