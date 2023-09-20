package org.codecop.rogue.template;

public class Room {

    public static final char PLAYER = '@';
    public static final char FLOOR = ' ';
    private char[][] baseLayout;

    public Room() {
        this.baseLayout = getBaseLayout();
        this.baseLayout[1][1] = PLAYER;
    }

    private static char[][] getBaseLayout() {
        return new char[][]{
                "#######\n".toCharArray(),
                "#     #\n".toCharArray(),
                "|     #\n".toCharArray(),
                "#     #\n".toCharArray(),
                "#######\n".toCharArray()
        };
    }

    @Override
    public String toString() {
        String map = "";
        for(char[] line: this.baseLayout) {
            map = map.concat(String.copyValueOf(line));
        }
        return map;
    }

    public void setNewPosition(int row, int column) {
        this.baseLayout = getBaseLayout();
        this.baseLayout[row][column] = PLAYER;
    }
}
