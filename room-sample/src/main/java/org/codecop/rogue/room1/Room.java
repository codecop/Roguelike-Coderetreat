package org.codecop.rogue.room1;

public class Room {

    private int playerPos = 1 * 7 + 4;

    public String display() {
        char[] x = ("" + //
                "#######\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#     |\n" + //
                "#     #\n" + //
                "#     #\n" + //
                "#######\n").toCharArray();
        x[playerPos] = '@';

        return new String(x);
    }

    public void playerMoves(char direction) {
        playerPos++;
    }

}
