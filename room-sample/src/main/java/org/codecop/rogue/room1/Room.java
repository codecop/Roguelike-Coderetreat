package org.codecop.rogue.room1;

public class Room {

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

    public String display() {
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

    public void playerMoves(char direction) {
        switch (direction) {
        case 'w':
            playerMoveUp();
            break;
        case 'd':
            playerMoveRight();
            break;
        case 's':
            playerMoveDown();
            break;
        case 'a':
            playerMoveLeft();
            break;
        }
    }

    private void playerMoveUp() {
        if (layoutIsEmptyAt(playerX, playerY - 1)) {
            playerY -= 1;
        }
    }

    private void playerMoveRight() {
        if (layoutIsEmptyAt(playerX + 1, playerY)) {
            playerX += 1;
        }
    }

    private void playerMoveDown() {
        if (layoutIsEmptyAt(playerX, playerY + 1)) {
            playerY += 1;
        }
    }

    private void playerMoveLeft() {
        if (layoutIsEmptyAt(playerX - 1, playerY)) {
            playerX -= 1;
        }
    }

    private boolean layoutIsEmptyAt(int x, int y) {
        return initialLayout[asIndex(x, y)] == ' ';
    }

    private int asIndex(int x, int y) {
        return y * (columns + 1) + x;
    }
}
