package org.codecop.rogue.tester;

import org.codecop.rogue.tester.checks.Checkers;
import org.codecop.rogue.tester.checks.Findings;
import org.codecop.rogue.tester.http.Api;
import org.codecop.rogue.tester.http.HttpClientApi;
import org.codecop.rogue.tester.model.Layout;
import org.codecop.rogue.tester.model.Maze;
import org.codecop.rogue.tester.model.Position;
import org.codecop.rogue.tester.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Main {

    private final Api api;
    private final String baseUrl;
    private final Findings findings = new Findings();
    private Layout lastLayout;

    public Main(Api api, String baseUrl) {
        this.api = api;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    /**
     * Get the room:
     * <pre>
     * get localhost:8004/empty/
     * </pre>
     * returns JSON body with
     * <pre>
     * {
     * "description": "You are in a little square room. There is nothing here.",
     * "layout": "#######\n#  @  #\n#     #\n#     |\n#     #\n#     #\n#######\n"
     * }
     * </pre>
     * (Description is optional.)
     */
    public void checkRoom() {
        String url = baseUrl;
        findings.info("Testing room " + url);
        Response response = api.get(url);

        Checkers checkers = Checkers.roomCheckers();
        checkers.check(findings, response);
        // debug(url, response);
        onErrorsExit();

        lastLayout = response.getLayout();
    }

    private void debug(String url, Response response) {
        System.out.println(url);
        System.out.println(response.statusCode);
        System.out.println(response.contentType);
        System.out.println(response.jsonBody);
        System.out.println(findings);
    }

    private final List<Function<Position, Position>> allDirections = Arrays.asList(
            Position::up,
            Position::down,
            Position::right,
            Position::left);

    /**
     * To walk around use:
     * <pre>
     * post localhost:8004/empty/walk?row=3&column=5
     * </pre>
     * sends the new coordinate to the room which has to update its internal
     * representation, so the `@` is in the right place. (A message is optional.)
     */
    public void checkWalk() {
        List<Function<Position, Position>> remainingDirections = new ArrayList<>(allDirections);
        checkWalkTo(remainingDirections);
    }

    private void checkWalkTo(List<Function<Position, Position>> remainingDirections) {
        Maze maze = lastLayout.toMaze();
        Position player = maze.getPositionOf('@');

        for (Function<Position, Position> direction : remainingDirections) {
            Position newPosition = direction.apply(player);
            if (maze.isFree(newPosition)) {
                checkWalkTo(newPosition);
                checkRoom();
                checkPlayerHasMovedTo(newPosition);

                remainingDirections.remove(direction);
                checkWalkTo(remainingDirections);
                return;
            }
        }

        if (remainingDirections.size() > 0) {
            findings.info("Could not walk to all directions");
        }
    }

    private void checkWalkTo(Position newPosition) {
        String url = baseUrl + "walk?row=" + newPosition.y + "&column=" + newPosition.x;
        findings.info("Testing walk " + url);
        Response response = api.post(url);

        Checkers walkCheckers = Checkers.walkCheckers();
        walkCheckers.check(findings, response);
        // debug(url, response);
        onErrorsExit();
    }

    private void checkPlayerHasMovedTo(Position newPosition) {
        Maze maze = lastLayout.toMaze();
        Position player = maze.getPositionOf('@');
        if (!player.equals(newPosition)) {
            findings.error("Expected player @ at " + newPosition + ", was " + player);
        }
        onErrorsExit();
    }

    /**
     * Check if the door is open or locked with:
     * <pre>
     * get localhost:8004/empty/open
     * </pre>
     * returns `true` or `false`. (If this is 404 then the door is open.)
     */
    public void checkDoor() {
        String url = baseUrl + "open";
        findings.info("Testing door " + url);
        Response response = api.get(url);

        Checkers checkers = Checkers.doorCheckers();
        checkers.check(findings, response);
        // debug(url, response);
        onErrorsExit();
    }

    private void onErrorsExit() {
        if (findings.hasErrors()) {
            System.out.println(findings);
            System.exit(1);
        }
    }

    private void exit() {
        System.out.println(findings);
        System.exit(0);
    }

    public static void main(String[] args) {
        String url = args[0];
        // String url = "http://localhost:8004/empty/";
        // String url = "http://localhost:8004/key/";
        // String url = "http://localhost:8004/monster/";
        // String url = "http://localhost:8004/minimal";

        Main main = new Main(new HttpClientApi(), url);
        main.checkRoom();
        main.checkWalk();
        main.checkDoor();
        main.exit();
    }

}
