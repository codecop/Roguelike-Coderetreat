package org.codecop.rogue.room2;

import java.util.Optional;

import jakarta.inject.Singleton;

@Singleton
public class SimpleRoom implements AnyRoom {

    private static final char SYMBOL_PLAYER = '@';

    private final char[] initialLayout = ("" + //
            "#######\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#     |\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#######\n").toCharArray();
    private final int columns = new String(initialLayout).replaceAll("\n.*", "").length();

    private int playerX = 3;
    private int playerY = 1;
    private boolean doorIsOpen = true;

    @Override
    public String layout() {
        char[] layout = initialLayout.clone();
        setPlayerTo(layout);
        return new String(layout);
    }

    private void setPlayerTo(char[] layout) {
        layout[playerAsIndex()] = SYMBOL_PLAYER;
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

    @Override
    public boolean canExitDoor() {
        return doorIsOpen;
    }

    @Override
    public String description() {
        return "You are in a little square room. There is nothing here.";
    }

}
