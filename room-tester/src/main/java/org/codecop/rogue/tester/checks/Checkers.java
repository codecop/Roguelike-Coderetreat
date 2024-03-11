package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

public class Checkers implements Checker {

    private final List<Checker> checkers = Arrays.asList(
            new RoomFormatChecker(),
            new RoomLayoutChecker(),
            new RoomDescriptionChecker()
    );

    public Findings check(Response response) {
        Findings findings = new Findings();
        check(findings, response);
        return findings;
    }

    @Override
    public void check(Findings findings, Response response) {
        try {
            checkAll(findings, response);
        } catch (JSONException e) {
            findings.error(e);
        }
    }

    private void checkAll(Findings findings, Response response) {
        checkers.forEach(checker -> checker.check(findings, response));
    }

}
