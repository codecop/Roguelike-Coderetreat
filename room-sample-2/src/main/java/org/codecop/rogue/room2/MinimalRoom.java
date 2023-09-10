package org.codecop.rogue.room2;

import jakarta.inject.Singleton;

@Singleton
public class MinimalRoom {

    private final char[] initialLayout = ("" + //
            "#####\n" + //
            "#c  |\n" + //
            "#####\n").toCharArray();
    private final int columns = new String(initialLayout).replaceAll("\n.*", "").length();

    private int playerX = 2;
    private int playerY = 1;

    public String layout() {
        char[] layout = initialLayout.clone();
        setPlayerTo(layout);
        return new String(layout);
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

    public void movesTo(int column, int row) {
        playerX = column;
        playerY = row;
    }

}
