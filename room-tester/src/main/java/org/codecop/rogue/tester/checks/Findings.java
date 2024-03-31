package org.codecop.rogue.tester.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Findings {

    private final List<Finding> findings = new ArrayList<>();

    public void error(String message) {
        add(Finding.error(message));
    }

    public void error(Exception e) {
        error(e.getMessage());
    }

    public void warn(String message) {
        add(Finding.warn(message));
    }

    public void info(String message) {
        add(Finding.info(message));
    }

    private void add(Finding finding) {
        if (findings.contains(finding)) {
            return;
        }
        findings.add(finding);
    }

    public Finding get(int index) {
        return findings.get(index);
    }

    public void forEach(Consumer<Finding> consumer) {
        findings.forEach(consumer);
    }

    public long count(Level level) {
        return findings.stream().filter(f -> f.level == level).count();
    }

    public boolean hasErrors() {
        return count(Level.ERROR) > 0;
    }

    @Override
    public String toString() {
        return findings.stream(). //
                map(Finding::toString). //
                collect(Collectors.joining("\n"));
    }
}
