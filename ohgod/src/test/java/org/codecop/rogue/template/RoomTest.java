package org.codecop.rogue.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomTest {

    @Test
    void shouldPrint() {
        Room room = new Room();

        String map = room.toString();

        assertEquals(
                        "#######\n" +
                        "#@    #\n" +
                        "|     #\n" +
                        "#     #\n" +
                        "#######\n",
                map);
    }
    @Test
    void shouldHaveDoor(){
        Room room = new Room();

        String map = room.toString();

        boolean hasDoor = map.indexOf('|') > 0;
        assertTrue(hasDoor );
    }

    @Test
    void shouldChangePosition(){
        Room room = new Room();
        String t0 = room.toString();
        room.setNewPosition(1,2);
        String t1 = room.toString();

        assertEquals(
                        "#######\n" +
                        "#@    #\n" +
                        "|     #\n" +
                        "#     #\n" +
                        "#######\n",
                t0);
        assertEquals(
                        "#######\n" +
                        "# @   #\n" +
                        "|     #\n" +
                        "#     #\n" +
                        "#######\n",
                t1);
    }

    @Test
    void shouldMoveRightTwice(){
        Room room = new Room();
        room.setNewPosition(1,3);
        room.setNewPosition(2,4);

        String t1 = room.toString();

        assertEquals(
                        "#######\n" +
                        "#     #\n" +
                        "|   @ #\n" +
                        "#     #\n" +
                        "#######\n",
                t1);
    }


}
