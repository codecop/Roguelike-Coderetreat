package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

public class RoomLayoutChecker implements Checker {

    private static final int MAX_SIZE = 15;

    @Override
    public void check(Findings findings, Response response) {
        JSONObject json = response.jsonBody;
        String layout = json.getString("layout");
        findings.info("Layout found: " + layout);

        if (layout == null) {
            findings.error("Expect layout, was null");
            return;
        }

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

        int countDoors = Strings.count(layout, '|');
        if (countDoors == 0) {
            findings.error("Expect at least one door '|', was none");
        }

        int countPlayers = Strings.count(layout, '@');
        if (countPlayers != 1) {
            findings.error("Expect one player '@', was none/more");
        }

        if (hasHoles(Strings.toCharArrayArray(lines))) {
            findings.error("Expected walls around level");
        }


      /*
    . room invalid (checks ondrej does)
     */

    }

    private boolean hasHoles(char[][] yx) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int y = 0; y < yx.length; y++) {
                for (int x = 0; x < yx[y].length; x++) {
                    if (yx[y][x] == '@') {

                        if (y - 1 < 0 || y + 1 >= yx.length || x - 1 < 0 || x + 1 >= yx[y].length) {
                            return true;
                        }

                        if (yx[y - 1][x] == ' ') {
                            yx[y - 1][x] = '@';
                            changed = true;
                        }
                        if (yx[y + 1][x] == ' ') {
                            yx[y + 1][x] = '@';
                            changed = true;
                        }
                        if (yx[y][x - 1] == ' ') {
                            yx[y][x - 1] = '@';
                            changed = true;
                        }
                        if (yx[y][x + 1] == ' ') {
                            yx[y][x + 1] = '@';
                            changed = true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
