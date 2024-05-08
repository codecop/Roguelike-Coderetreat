package org.codecop.rogue.tester.checks;

import java.util.Objects;

public class Finding {
    public final Level level;
    public final String message;

    public static Finding fatal(String message) {
        return new Finding(Level.FATAL, message);
    }

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
        if (message.contains("\n") && message.contains(": ")) {
            StringBuilder intendedMessage = new StringBuilder();

            int firstColon = message.indexOf(':');
            String description = level.name() + '\t' + message.substring(0, firstColon + 2);
            intendedMessage.append(description);

            String[] remainingMessages = message.substring(firstColon + 2).split("\n");
            intendedMessage.append(remainingMessages[0]);
            intendedMessage.append('\n');

            String intent = description.replaceAll("[^\t]", " ");
            for (int i = 1; i < remainingMessages.length; i++) {
                intendedMessage.append(intent);
                intendedMessage.append(remainingMessages[i]);
                intendedMessage.append('\n');
            }

            return intendedMessage.toString();
        }
        return level.name() + '\t' + message;
    }
}
