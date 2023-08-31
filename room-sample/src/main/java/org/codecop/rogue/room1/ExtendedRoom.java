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

    private static final char SYMBOL_CHEST = 'c';
    private static final char SYMBOL_KEY = 'k';

    private final char[] initialLayout = ("" + //
            "#######\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#     |\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#######\n").toCharArray();

    private final int numberColumns = new String(initialLayout).replaceAll("\n.*", "").length();

    private int playerX = 3;
    private int playerY = 1;

    private int chestX = 3;
    private int chestY = 5;
    private boolean hasChest = true;
    private boolean hasKey = false;
    private boolean doorIsOpen = false;

    @Override
    public String description() {
        if (hasChest) {
            return "A locked room. There is a <c>hest at the South wall.";
        }
        return "A locked room. You found the <k>ey.";
    }

    @Override
    public String display() {
        char[] layout = initialLayout.clone();
        drawPlayer(layout);
        drawChest(layout);
        drawKey(layout);
        return new String(layout);
    }

    private void drawPlayer(char[] layout) {
        layout[playerAsIndex()] = SYMBOL_PLAYER;
    }

    private int playerAsIndex() {
        return asIndex(playerX, playerY);
    }

    private void drawChest(char[] layout) {
        if (hasChest) {
            layout[asIndex(chestX, chestY)] = SYMBOL_CHEST;
        }
    }

    private void drawKey(char[] layout) {
        if (hasKey) {
            layout[asIndex(chestX, chestY)] = SYMBOL_KEY;
        }
    }

    @Override
    public void playerMoves(char direction) {
        switch (direction) {
        case 'w':
            moveTo(playerX, playerY - 1);
            break;
        case 'd':
            moveTo(playerX + 1, playerY);
            break;
        case 's':
            moveTo(playerX, playerY + 1);
            break;
        case 'a':
            moveTo(playerX - 1, playerY);
            break;
        case ' ':
            boolean nearPlayer = isNearPlayer(chestX, chestY);
            if (hasChest && nearPlayer) {
                hasChest = false;
                hasKey = true;
            } else if (hasKey && nearPlayer) {
                hasChest = false;
                hasKey = false;
                doorIsOpen = true;
            }
            break;
        }
    }

    private void moveTo(int x, int y) {
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

    private boolean isNearPlayer(int x, int y) {
        return Math.abs(playerX - x) <= 1 && Math.abs(playerY - y) <= 1;
    }

    private int asIndex(int x, int y) {
        return y * (numberColumns + 1) + x;
    }

    public void setDoorOpen(boolean isOpen) {
        doorIsOpen = isOpen;
    }

    @Override
    public List<Item> getLegend() {
        if (hasChest) {
            return Arrays.asList(new Item(SYMBOL_CHEST, "a sturdy chest"));
        }
        return Arrays.asList(new Item(SYMBOL_KEY, "a small iron key"));
    }
}
