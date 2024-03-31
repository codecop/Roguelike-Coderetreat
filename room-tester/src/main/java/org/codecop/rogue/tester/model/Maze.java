package org.codecop.rogue.tester.model;

public class Maze {
    private static final char EMPTY = ' ';

    private final char[][] yx;

    public Maze(String layout) {
        this(Strings.toCharArrayArray(layout.split("\n")));
    }

    public Maze(char[][] yxField) {
        this.yx = yxField;
    }

    public int height() {
        return yx.length;
    }

    public double width(int y) {
        return yx[y].length;
    }

    public void fillAllEmptyFrom(char player) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int y = 0; y < height(); y++) {
                for (int x = 0; x < width(y); x++) {
                    if (yx[y][x] == player) {

                        if (isAtBorder(y, x)) {
                            continue;
                        }

                        if (yx[y - 1][x] == EMPTY) {
                            yx[y - 1][x] = player;
                            changed = true;
                        }
                        if (yx[y + 1][x] == EMPTY) {
                            yx[y + 1][x] = player;
                            changed = true;
                        }
                        if (yx[y][x - 1] == EMPTY) {
                            yx[y][x - 1] = player;
                            changed = true;
                        }
                        if (yx[y][x + 1] == EMPTY) {
                            yx[y][x + 1] = player;
                            changed = true;
                        }
                    }
                }
            }
        }
    }

    private boolean isAtBorder(int y, int x) {
        return y - 1 < 0 || y + 1 >= height() ||
                x - 1 < 0 || x + 1 >= width(y);
    }

    public boolean touchesOuterWall(char player) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(y); x++) {
                if (yx[y][x] == player && isAtBorder(y, x)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canReach(char player, char item) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(y); x++) {
                if (yx[y][x] == player) {

                    if (isAtBorder(y, x)) {
                        // player should not be at border, just avoid IndexOutOfBounds
                        continue;
                    }

                    if (isItemNextTo(item, y, x)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isItemNextTo(char item, int y, int x) {
        return yx[y - 1][x] == item || yx[y + 1][x] == item ||
                yx[y][x - 1] == item || yx[y][x + 1] == item;
    }

    public Position getPositionOf(char c) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(y); x++) {
                if (yx[y][x] == c) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }
}
