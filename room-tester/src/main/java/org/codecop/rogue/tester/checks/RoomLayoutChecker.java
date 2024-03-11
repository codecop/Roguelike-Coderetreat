package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

public class RoomLayoutChecker implements Checker {
    @Override
    public void check(Findings findings, Response response) {
        JSONObject json = response.jsonBody;
        String layout = json.getString("layout");
        findings.info("Layout found: " + layout);

        if (layout == null || layout.length() < (3 + 1) * 3) {
            findings.error("Expect layout, was short " + layout);
            return;
        }

        int countPlayers = Strings.count(layout, '@');
        if (countPlayers != 1) {
            findings.error("Expect one player '@', was " + layout);
        }

        int countDoors = Strings.count(layout, '|');
        if (countDoors == 0) {
            findings.error("Expect at least one door '|', was " + layout);
        }

        /*
    . room invalid (checks ondrej does)
    . Json broken (" and \n)

A maximum of 15 times 15 including outer walls.
 */

    }

}
