package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Response;

import java.util.Optional;

public class RoomItemChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        String layout = response.getLayout();
        String itemsOrMonsters = layout.replaceAll("[# @|\n]", "");

        boolean hasItemsOrMonsters = itemsOrMonsters.length() > 0;
        if (!hasItemsOrMonsters) {
            return;
        }

        findings.info("Items or monsters found: " + itemsOrMonsters);

        Optional<String> optionalDescription = response.getDescription();
        if (optionalDescription.isPresent()) {
            String description = optionalDescription.get();

            for (char item : itemsOrMonsters.toCharArray()) {
                String mnemonic = "<" + item + ">";
                if (!description.contains(mnemonic)) {
                    findings.warn("Expect mnemonic " + mnemonic + " for item in description, was " + description);
                }
            }

        } else {
            findings.warn("Expect description with mnemonics <x> for items or monsters " + itemsOrMonsters);
        }

    }

}
