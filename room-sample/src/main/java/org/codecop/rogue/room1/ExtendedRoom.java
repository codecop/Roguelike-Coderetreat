package org.codecop.rogue.room1;

import java.util.Arrays;
import java.util.List;

import jakarta.inject.Singleton;

@Singleton
public class ExtendedRoom implements AnyRoom {

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
    private boolean doorIsOpen = false;

    private boolean hasChest = true;
    private int chestX = 3;
    private int chestY = 5;
    private boolean hasKey = false;

    @Override
    public String display() {
        char[] layout = initialLayout.clone();
        setPlayerTo(layout);
        if (hasChest) {
            layout[asIndex(chestX, chestY)] = 'c';
        }
        if (hasKey) {
            layout[asIndex(chestX, chestY)] = 'k';
        }
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
        case ' ':
            hasChest = false;
            hasKey = true;
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
    public String description() {
        if (hasChest) {
            return "A locked room. Can you escape?";
        }
        return "A locked room. You found the key.";
    }

    @Override
    public List<Item> getLegend() {
        if (hasChest) {
            return Arrays.asList(new Item('c', "a sturdy chest"));
        }
        return Arrays.asList(new Item('k', "a small iron key"));
    }
}
