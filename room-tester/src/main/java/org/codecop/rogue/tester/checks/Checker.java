package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;

import java.util.List;

public interface Checker {
    void check(List<Finding> findings, Response response);
}
