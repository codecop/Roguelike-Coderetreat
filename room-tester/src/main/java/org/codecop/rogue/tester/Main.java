package org.codecop.rogue.tester;

import org.codecop.rogue.tester.checks.Checkers;
import org.codecop.rogue.tester.checks.Findings;
import org.codecop.rogue.tester.http.Api;
import org.codecop.rogue.tester.http.HttpClientApi;
import org.codecop.rogue.tester.model.Layout;
import org.codecop.rogue.tester.model.Maze;
import org.codecop.rogue.tester.model.Position;
import org.codecop.rogue.tester.model.Response;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

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

    /**
     * To walk around use:
     * <pre>
     * post localhost:8004/empty/walk?row=3&column=5
     * </pre>
     * sends the new coordinate to the room which has to update its internal
     * representation, so the `@` is in the right place.
     */
    public void checkMovement() {
        Maze maze = lastLayout.toMaze();
        Position player = maze.getPositionOf('@');
        List<Supplier<Position>> possiblePositions = Arrays.asList(
                player::up,
                player::right,
                player::down,
                player::left);
        for (Supplier<Position> direction : possiblePositions) {
            Position newPosition = direction.get();
            if (maze.isFree(newPosition)) {
                checkMovementTo(newPosition);
                // stop at first free position
                break;
            }
        }
    }

    private void checkMovementTo(Position newPosition) {
        String url = baseUrl + "walk?row=" + newPosition.y + "&column=" + newPosition.x;
        findings.info("Testing walk " + url);
        Response response = api.post(url);

        Checkers walkCheckers = Checkers.walkCheckers();
        walkCheckers.check(findings, response);
        // debug(url, response);
        onErrorsExit();

        checkRoom();

        Maze maze = lastLayout.toMaze();
        Position player = maze.getPositionOf('@');
        if (!player.equals(newPosition)) {
            findings.error("Expected player @ at " + newPosition + ", was " + player);
        }
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
        // System.out.println("Testing room " + args[0]);
        // String url = "http://localhost:8004/key/";
        // String url = "http://localhost:8004/monster/";
        String url = "http://localhost:8004/minimal";

        Main main = new Main(new HttpClientApi(), url);
        main.checkRoom();
        main.checkMovement();
        main.exit();

        /*
Check if the door is open or locked with:

    get http://localhost:8004/empty/open

returns `true` or `false`. (If this is 404 then the door is open.)
         */
        Api api = new HttpClientApi();
        Response response = api.get("http://localhost:8004/empty/open");
        System.out.println(response.statusCode); // 200 or 404
        System.out.println(response.contentType); // content-type=[application/json]
        System.out.println(response.jsonBody); // true or false
        // findings = checkers.check(response);
        // System.out.println(findings);

        response = api.get("http://localhost:8004/minimal/open");
        // findings = checkers.check(response);
        // System.out.println(findings);


        System.exit(0);
    }

}
