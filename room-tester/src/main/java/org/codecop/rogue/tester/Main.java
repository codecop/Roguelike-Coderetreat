package org.codecop.rogue.tester;

import org.codecop.rogue.tester.checks.Checkers;
import org.codecop.rogue.tester.checks.Findings;
import org.codecop.rogue.tester.http.Api;
import org.codecop.rogue.tester.http.HttpClientApi;
import org.codecop.rogue.tester.model.Layout;
import org.codecop.rogue.tester.model.Maze;
import org.codecop.rogue.tester.model.Position;
import org.codecop.rogue.tester.model.Response;

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
        Checkers checkers = Checkers.roomCheckers();

        String url = baseUrl;
        findings.info("Testing room " + url);
        Response response = api.get(url);
        checkers.check(findings, response);
        debug(url, response);
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
        Checkers walkCheckers = Checkers.walkCheckers();

        Maze maze = lastLayout.toMaze();
        Position p = maze.getPositionOf('@');
//        maze.isFree(p.up());
//        maze.isFree(p.right());
//        maze.isFree(p.down());
//        maze.isFree(p.left());
//
//        String url = baseUrl + "walk?";
//        findings.info("Testing walk " + url);
//        Response response = api.post(url);
//        walkCheckers.check(findings, response);
//        debug(url, response);
        onErrorsExit();

        checkRoom();
    }

    private void onErrorsExit() {
        if (findings.hasErrors()) {
            System.out.println(findings);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // System.out.println("Testing room " + args[0]);
        String url1 = "http://localhost:8004/key/";
        String url = "http://localhost:8004/monster/";

        Main main = new Main(new HttpClientApi(), url);
        main.checkRoom();
        main.checkMovement();

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

    /*
    2. Tester für Clients selber laufen lassen, als Java Tool JAR bzw. Docker
  . most mistakes were:
    . movement broken means not working
      . not moving at all
      . wrong direction on arrows
     */

}
