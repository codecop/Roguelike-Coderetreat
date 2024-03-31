package org.codecop.rogue.tester.model;

public class Maze {
    private static final char EMPTY = ' ';

    private final char[][] yx;

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
            for (int y = 0; y < yx.length; y++) {
                for (int x = 0; x < yx[y].length; x++) {
                    if (yx[y][x] == player) {

                        if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
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

    public boolean touchesOuterWall(char player) {
        for (int y = 0; y < yx.length; y++) {
            for (int x = 0; x < yx[y].length; x++) {
                if (yx[y][x] == player) {
                    if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canReach(char player, char item) {
        for (int y = 0; y < yx.length; y++) {
            for (int x = 0; x < yx[y].length; x++) {
                if (yx[y][x] == player) {

                    if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
                        continue;
                    }

                    if (yx[y - 1][x] == item || yx[y + 1][x] == item ||
                            yx[y][x - 1] == item || yx[y][x + 1] == item) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
