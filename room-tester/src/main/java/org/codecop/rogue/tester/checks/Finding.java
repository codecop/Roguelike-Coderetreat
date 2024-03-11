package org.codecop.rogue.tester.checks;

import java.util.Objects;

public class Finding {
    public final Level level;
    public final String message;

    public static Finding error(String message) {
        return new Finding(Level.ERROR, message);
    }

    public static Finding warn(String message) {
        return new Finding(Level.WARNING, message);
    }

    public static Finding info(String message) {
        return new Finding(Level.INFO, message);
    }

    public Finding(Level level, String message) {
        this.level = level;
        this.message = message;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Finding other = (Finding) o;
        return level == other.level && message.equals(other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, message);
    }

    @Override
    public String toString() {
        return level.name() + '\t' + message;
    }
}
