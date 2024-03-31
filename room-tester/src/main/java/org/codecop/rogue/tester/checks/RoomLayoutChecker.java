package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

/**
 * Checks the layout of the room:
 * <ul>
 *     <li>Min and max size of whole response using newlines.</li>
 *     <li>Must have a door.</li>
 *     <li>Must have a player which should be able to reach the door.</li>
 *     <li>Must have walls around everything.</li>
 * </ul>
 */
public class RoomLayoutChecker implements Checker {

    private static final int MAX_SIZE = 15;
    public static final char DOOR = '|';
    public static final char PLAYER = '@';

    @Override
    public void check(Findings findings, Response response) {
        JSONObject json = response.jsonBody;
        String layout = json.getString("layout");
        boolean hasLayout = layout != null;
        if (!hasLayout) {
            findings.error("Expect layout, was null");
            return;
        }
        findings.info("Layout found: " + layout);

        String[] lines = layout.split("\n");
        if (lines.length < 3) {
            findings.error("Expect layout min height 3, was " + lines.length);
        }
        if (lines.length > MAX_SIZE) {
            findings.warn("Expect layout max height " + MAX_SIZE + ", was " + lines.length);
        }

        for (String line : lines) {
            if (line.length() < 3) {
                findings.error("Expect layout min width 3, was " + line.length());
            }
            if (line.length() > MAX_SIZE) {
                findings.warn("Expect layout max width " + MAX_SIZE + ", was " + line.length());
            }
        }

        // TODO Must all lines be same length?

        int countDoors = Strings.count(layout, DOOR);
        if (countDoors == 0) {
            findings.error("Expect at least one door '" + DOOR + "', was none");
        }

        int countPlayers = Strings.count(layout, PLAYER);
        if (countPlayers != 1) {
            findings.error("Expect one player '" + PLAYER + "', was none/more");
        }

        char[][] yxField = Strings.toCharArrayArray(lines);
        fillReachableFromPlayer(yxField);

        if (hasHolesInOuterWall(yxField)) {
            findings.error("Expected walls around level");
        }

        if (!canReachDoor(yxField)) {
            findings.warn("Expected free way to door");
        }
    }

    private void fillReachableFromPlayer(char[][] yx) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int y = 0; y < yx.length; y++) {
                for (int x = 0; x < yx[y].length; x++) {
                    if (yx[y][x] == PLAYER) {

                        if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
                            continue;
                        }

                        if (yx[y - 1][x] == ' ') {
                            yx[y - 1][x] = PLAYER;
                            changed = true;
                        }
                        if (yx[y + 1][x] == ' ') {
                            yx[y + 1][x] = PLAYER;
                            changed = true;
                        }
                        if (yx[y][x - 1] == ' ') {
                            yx[y][x - 1] = PLAYER;
                            changed = true;
                        }
                        if (yx[y][x + 1] == ' ') {
                            yx[y][x + 1] = PLAYER;
                            changed = true;
                        }
                    }
                }
            }
        }
    }

    private boolean hasHolesInOuterWall(char[][] yx) {
        for (int y = 0; y < yx.length; y++) {
            for (int x = 0; x < yx[y].length; x++) {
                if (yx[y][x] == PLAYER) {
                    if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canReachDoor(char[][] yx) {
        for (int y = 0; y < yx.length; y++) {
            for (int x = 0; x < yx[y].length; x++) {
                if (yx[y][x] == PLAYER) {

                    if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
                        continue;
                    }

                    if (yx[y - 1][x] == DOOR || yx[y + 1][x] == DOOR ||
                            yx[y][x - 1] == DOOR || yx[y][x + 1] == DOOR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
