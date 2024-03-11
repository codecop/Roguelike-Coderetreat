package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;

public interface Checker {
    void check(Findings findings, Response response);
}
