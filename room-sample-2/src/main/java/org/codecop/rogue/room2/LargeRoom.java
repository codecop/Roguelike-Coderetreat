package org.codecop.rogue.room2;

import java.util.Optional;

import jakarta.inject.Singleton;

@Singleton
public class LargeRoom implements AnyRoom {

    private static final int SIZE = 50;

    private final char[] initialLayout = ("" + //
            times("#", SIZE) + "\n" + //
            times("#" + times(" ", SIZE - 2) + "#\n", (SIZE - 3) / 2) + //
            "#" + times(" ", SIZE - 2) + "|\n" + //
            times("#" + times(" ", SIZE - 2) + "#\n", (SIZE - 3) / 2) + //
            times("#", SIZE) + "\n").toCharArray();
    private final int columns = new String(initialLayout).replaceAll("\n.*", "").length();

    private int playerX = 1;
    private int playerY = 1;

    @Override
    public String layout() {
        char[] layout = initialLayout.clone();
        setPlayerTo(layout);
        return new String(layout);
    }

    private String times(String s, int number) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < number; i++) {
            buf.append(s);
        }
        return buf.toString();
    }

    private void setPlayerTo(char[] layout) {
        layout[playerAsIndex()] = '@';
    }

    private int playerAsIndex() {
        return asIndex(playerX, playerY);
    }

    private int asIndex(int x, int y) {
        return y * (columns + 1) + x;
    }

    @Override
    public Optional<String> movesTo(Position newPosition) {
        playerX = newPosition.getColumn();
        playerY = newPosition.getRow();
        return Optional.empty();
    }

}
