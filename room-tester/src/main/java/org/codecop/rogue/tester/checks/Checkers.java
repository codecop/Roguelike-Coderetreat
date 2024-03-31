package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

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
