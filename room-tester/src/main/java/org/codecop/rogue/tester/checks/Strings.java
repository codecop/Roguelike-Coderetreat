package org.codecop.rogue.tester.checks;

public class Strings {
    public static int count(String layout, char c) {
        int count = 0;
        for (int i = 0; i < layout.length(); i++) {
            if (layout.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}
