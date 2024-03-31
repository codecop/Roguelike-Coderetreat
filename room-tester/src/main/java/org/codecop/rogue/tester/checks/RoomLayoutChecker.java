package org.codecop.rogue.tester.checks;

import org.codecop.rogue.tester.model.Layout;
import org.codecop.rogue.tester.model.Maze;
import org.codecop.rogue.tester.model.Response;

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
        Layout layout = response.getLayout();
        findings.info("Layout found: " + layout);

        int countDoors = layout.count(DOOR);
        if (countDoors == 0) {
            findings.error("Expect at least one door '" + DOOR + "', was none");
        }

        int countPlayers = layout.count(PLAYER);
        if (countPlayers != 1) {
            findings.error("Expect one player '" + PLAYER + "', was none/more");
        }

        Maze maze = layout.toMaze();

        int height = maze.height();
        if (height < 3) {
            findings.error("Expect layout min height 3, was " + height);
        }
        if (height > MAX_SIZE) {
            findings.warn("Expect layout max height " + MAX_SIZE + ", was " + height);
        }

        // TODO Must all lines be same length?
        for (int y = 0; y < height; y++) {
            double width = maze.width(y);
            if (width < 3) {
                findings.error("Expect layout min width 3, was " + width);
            }
            if (width > MAX_SIZE) {
                findings.warn("Expect layout max width " + MAX_SIZE + ", was " + width);
            }
        }

        maze.fillAllEmptyFrom(PLAYER);
        if (maze.touchesOuterWall(PLAYER)) {
            findings.error("Expected walls around level");
        }

        if (!maze.canReach(PLAYER, DOOR)) {
            findings.warn("Expected free way to door");
        }
    }

}
