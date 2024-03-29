package org.codecop.rogue.tester.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Findings {

    private final List<Finding> findings = new ArrayList<>();

    public void error(String message) {
        findings.add(Finding.error(message));
    }

    public void error(Exception e) {
        error(e.getMessage());
    }

    public void warn(String message) {
        findings.add(Finding.warn(message));
    }

    public void info(String message) {
        findings.add(Finding.info(message));
    }

    public Finding get(int index) {
        return findings.get(index);
    }

    @Override
    public String toString() {
        return findings.stream(). //
                map(Finding::toString). //
                collect(Collectors.joining("\n"));
    }

    public void forEach(Consumer<Finding> consumer) {
        findings.forEach(consumer);
    }

}
