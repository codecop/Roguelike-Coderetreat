package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

import java.util.Optional;

public class MessageTextChecker implements Checker {

    private final String kind;

    public MessageTextChecker(String kind) {
        this.kind = kind;
    }

    @Override
    public void check(Findings findings, Response response) {
        Optional<String> optionalMessage = response.getMessage();
        if (optionalMessage.isEmpty()) {
            findings.infoOptional(kind + " message");
            return;
        }

        String message = optionalMessage.get();
        findings.infoFound(kind + " message", message);

        if (message.length() < 10) {
            findings.warn("Expect useful message, was short " + message);
            if (message.isEmpty()) {
                return;
            }
        }

        char firstLetter = message.charAt(0);
        if (!Character.isUpperCase(firstLetter)) {
            findings.warn("Start message with upper case, was " + firstLetter);
        }

        char lastLetter = message.charAt(message.length() - 1);
        if (lastLetter != '.' && lastLetter != '!') {
            findings.warn("End message with . or !, was " + lastLetter);
        }

        if (message.indexOf('\n') != -1) {
            findings.warn("Message should not have newline, was " + message);
        }

    }

}
