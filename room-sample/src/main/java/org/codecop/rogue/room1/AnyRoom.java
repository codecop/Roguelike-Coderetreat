package org.codecop.rogue.room1;

import java.util.List;

public interface AnyRoom {

    String description();
    // TODO checker: Has some length and few words, few spaces.

    String display();
    // TODO checker: has a @, has a | or -, has # around outside
    // if it has other than # and ' ' -> legend (or with <x> description)

    List<Item> getLegend();
    // TODO checker: needs legend for all extra items, bzw nicht notwendig wenn in description sind.

    void playerMoves(char direction);
    // TODO checker: check for space around @ and send one move to get updated display

}
