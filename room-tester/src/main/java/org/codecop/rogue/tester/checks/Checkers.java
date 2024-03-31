package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;

import java.util.Arrays;
import java.util.List;

public class Checkers implements Checker {

    private final List<Checker> checkers;

    public static Checkers roomCheckers() {
        return new Checkers(Arrays.asList(
                new RoomFormatChecker(),
                new RoomLayoutChecker(),
                new RoomDescriptionChecker(),
                new RoomItemChecker()
        ));
    }

    public Checkers(List<Checker> checkers) {
        this.checkers = checkers;
    }

    public Findings check(Response response) {
        Findings findings = new Findings();
        check(findings, response);
        return findings;
    }

    @Override
    public void check(Findings findings, Response response) {
        try {
            checkAll(findings, response);
        } catch (Exception e) {
            findings.error(e);
        }
    }

    private void checkAll(Findings findings, Response response) {
        checkers.forEach(checker -> checker.check(findings, response));
    }

}
