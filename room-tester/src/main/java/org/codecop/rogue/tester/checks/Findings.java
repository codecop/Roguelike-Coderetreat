package org.codecop.rogue.tester.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Findings {

    private final List<Finding> findings = new ArrayList<>();

    public void fatal(String message) {
        add(Finding.fatal(message));
    }

    public void fatal(Exception e) {
        fatal(e.toString());
    }

    public void error(String message) {
        add(Finding.error(message));
    }

    public void warn(String message) {
        add(Finding.warn(message));
    }

    public void info(String message) {
        add(Finding.info(message));
    }

    public void infoFound(String key, String value) {
        info(key + " found: " + value);
    }

    public void infoOptional(String key) {
        info("Could have optional " + key + ", was none");
    }

    public void testingSection(String subject, String url) {
        String header = "==========";
        info("Testing: " + subject + " " + header + "\n" + url);
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

    public boolean isFatal() {
        return count(Level.FATAL) > 0;
    }

    @Override
    public String toString() {
        return findings.stream(). //
                map(Finding::toString). //
                collect(Collectors.joining("\n"));
    }
}
