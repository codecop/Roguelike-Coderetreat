package org.codecop.rogue.room1;

public class Room {

    private int playerPos = 1 * (7 + 1) + 3;
    private char[] initial = ("" + //
            "#######\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#     |\n" + //
            "#     #\n" + //
            "#     #\n" + //
            "#######\n").toCharArray();

    public String display() {
        char[] x = initial.clone();
        setPlayer(x);
        return new String(x);
    }

    private void setPlayer(char[] x) {
        x[playerPos] = '@';
    }

    public void playerMoves(char direction) {
        switch (direction) {
        case 'd':
            if (free(playerPos + 1))
                playerPos += 1;
            break;
        case 'a':
            if (free(playerPos - 1))
                playerPos -= 1;
            break;
        case 'w':
            if (free(playerPos - (7 + 1)))
                playerPos -= (7 + 1);
            break;
        case 's':
            if (free(playerPos + (7 + 1)))
                playerPos += (7 + 1);
            break;
        }
    }

    private boolean free(int pos) {
        return initial[pos] == ' ';
    }
}
