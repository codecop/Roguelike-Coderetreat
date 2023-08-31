package org.codecop.rogue.room1;

import java.util.List;

import jakarta.inject.Singleton;

@Singleton
public class Room implements AnyRoom {

    private static final char SYMBOL_PLAYER = '@';
    private static final char SYMBOL_DOOR_VERTICAL = '|';
    private static final char SYMBOL_DOOR_HORICONTAL = '-';
    private static final char SYMBOL_NOTHING = ' ';

    private final char[] initialLayout = ("" + //
            "#######\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#     |\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#######\n").toCharArray();
    private final int columns = new String(initialLayout).replaceAll("\n.*", "").length();
    // private final int rows = new String(initialLayout).replaceAll("[^\n]", "").length();

    private int playerX = 3;
    private int playerY = 1;
    private boolean doorIsOpen = true;

    @Override
    public String display() {
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

    @Override
    public void playerMoves(char direction) {
        switch (direction) {
        case 'w':
            tryMove(playerX, playerY - 1);
            break;
        case 'd':
            tryMove(playerX + 1, playerY);
            break;
        case 's':
            tryMove(playerX, playerY + 1);
            break;
        case 'a':
            tryMove(playerX - 1, playerY);
            break;
        }
    }

    private void tryMove(int x, int y) {
        if (layoutIsEmptyAt(x, y)) {
            playerX = x;
            playerY = y;
            return;
        }
        if (doorIsOpen && layoutIsDoorAt(x, y)) {
            playerX = x;
            playerY = y;
        }
    }

    private boolean layoutIsEmptyAt(int x, int y) {
        return initialLayout[asIndex(x, y)] == SYMBOL_NOTHING;
    }

    private boolean layoutIsDoorAt(int x, int y) {
        return initialLayout[asIndex(x, y)] == SYMBOL_DOOR_VERTICAL
                || initialLayout[asIndex(x, y)] == SYMBOL_DOOR_HORICONTAL;
    }

    private int asIndex(int x, int y) {
        return y * (columns + 1) + x;
    }

    public void setDoorOpen(boolean isOpen) {
        doorIsOpen = isOpen;
    }

    @Override
    public String decription() {
        return "You are in a little square room. There is nothing here.";
    }

    @Override
    public List<Item> getLegend() {
        return null;
    }
}
