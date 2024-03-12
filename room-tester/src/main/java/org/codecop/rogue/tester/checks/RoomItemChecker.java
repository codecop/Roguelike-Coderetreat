package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.http.Response;
import org.json.JSONObject;

public class RoomItemChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        JSONObject json = response.jsonBody;
        String layout = json.getString("layout");
        String itemsOrMonsters = layout.replaceAll("[# @|\n]", "");

        if (itemsOrMonsters.length() > 0) {
            findings.info("Items or monsters found: " + itemsOrMonsters);

            if (json.has("description")) {
                String description = json.getString("description");

                for (char item : itemsOrMonsters.toCharArray()) {
                    String mnemonic = "<" + item + ">";
                    if (!description.contains(mnemonic)) {
                        findings.warn("Expect mnemonic " + mnemonic + " for item, was " + description);
                    }
                }

            } else {
                findings.warn("Expect description with mnemonics <x> for items or monsters " + itemsOrMonsters);
            }
        }

    }

}
