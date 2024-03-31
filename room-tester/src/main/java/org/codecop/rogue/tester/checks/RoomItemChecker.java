package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Item;
import org.codecop.rogue.tester.model.Layout;
import org.codecop.rogue.tester.model.Response;

import java.util.List;
import java.util.Optional;

public class RoomItemChecker implements Checker {

    @Override
    public void check(Findings findings, Response response) {
        Layout layout = response.getLayout();
        List<Item> itemsOrMonsters = layout.itemsOrMonsters();
        boolean hasItemsOrMonsters = itemsOrMonsters.size() > 0;
        if (!hasItemsOrMonsters) {
            return;
        }

        findings.info("Items or monsters found: " + itemsOrMonsters);

        Optional<String> optionalDescription = response.getDescription();
        if (optionalDescription.isEmpty()) {
            findings.warn("Expect description with mnemonics <x> for items or monsters " + itemsOrMonsters);
            return;
        }

        String description = optionalDescription.get();

        for (Item item : itemsOrMonsters) {
            String mnemonic = item.getMnemonic();
            if (!description.contains(mnemonic)) {
                findings.warn("Expect mnemonic " + mnemonic + " for item in description, was " + description);
            }
        }

    }

    // Check "legend":[{"item":"c","description":"a sturdy chest"}]}
    // if there is legend, no need for mnemonics
}
