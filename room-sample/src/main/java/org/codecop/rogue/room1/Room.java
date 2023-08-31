package org.codecop.rogue.room1;

public class Room {

    private int playerPos = 1 * (7 + 1) + 3;

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
        switch (direction) {
        case 'd':
            playerPos += 1;
            break;
        case 'a':
            playerPos -= 1;
            break;
        case 'w':
            playerPos -= (7 + 1);
            break;
        case 's':
            playerPos += (7 + 1);
            break;
        }
    }

}
