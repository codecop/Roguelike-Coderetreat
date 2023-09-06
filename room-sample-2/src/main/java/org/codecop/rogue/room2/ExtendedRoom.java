package org.codecop.rogue.room2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Singleton;

@Singleton
public class ExtendedRoom implements AnyRoom {

    private static final char SYMBOL_PLAYER = '@';

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
    public String layout() {
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

    private int asIndex(int x, int y) {
        return y * (numberColumns + 1) + x;
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
    public Optional<String> interactWith(Item item) {
        if (item.getItem().charAt(0) == SYMBOL_CHEST) {
            hasChest = false;
            hasKey = true;
            return Optional.of("There is a key in the chest.");
        }
        if (item.getItem().charAt(0) == SYMBOL_KEY) {
            hasChest = false;
            hasKey = false;
            doorIsOpen = true;
        }
        return Optional.empty();
    }

    @Override
    public List<Item> getLegend() {
        if (hasChest) {
            return Arrays.asList(new Item(SYMBOL_CHEST, "a sturdy chest"));
        }
        return Arrays.asList(new Item(SYMBOL_KEY, "a small iron key"));
    }

}
