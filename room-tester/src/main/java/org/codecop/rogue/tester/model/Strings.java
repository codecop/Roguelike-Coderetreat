package org.codecop.rogue.tester.model;

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

    public static char[][] toCharArrayArray(String[] lines) {
        char[][] result = new char[lines.length][];
        for (int y = 0; y < lines.length; y++) {
            result[y] = lines[y].toCharArray();
        }
        return result;
    }
}
