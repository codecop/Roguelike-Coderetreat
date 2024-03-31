package org.codecop.rogue.tester;

import org.codecop.rogue.tester.checks.Checkers;
import org.codecop.rogue.tester.checks.Findings;
import org.codecop.rogue.tester.http.Api;
import org.codecop.rogue.tester.http.HttpClientApi;
import org.codecop.rogue.tester.model.Response;

public class Main {

    public static void main(String[] args) {
        // System.out.println("Testing room " + args[0]);

        Api api = new HttpClientApi();
        Checkers checkers = Checkers.roomCheckers();
        /*
Get the room:

    get localhost:8004/empty/

returns JSON body with

    {
      "description": "You are in a little square room. There is nothing here.",
      "layout": "#######\n#  @  #\n#     #\n#     |\n#     #\n#     #\n#######\n"
    }
(Description is optional.)
{"description":"A locked room. There is a <c>hest at the South wall.",
"layout":"#######\n#  @  #\n#     #\n#     |\n#     #\n#  c  #\n#######\n",
"legend":[{"item":"c","description":"a sturdy chest"}]}
         */
        // HttpResponse<String> response = get("http://localhost:8004/key/");
        Response response = api.get("http://localhost:8004/monster/");
        System.out.println(response.statusCode); // 200
        System.out.println(response.contentType); // content-type=[application/json]
        System.out.println(response.jsonBody);
        Findings findings = new Findings();
        checkers.check(findings, response);
        System.out.println(findings);
        // see https://www.baeldung.com/java-jsonobject-get-value

        /*
Check if the door is open or locked with:

    get http://localhost:8004/empty/open

returns `true` or `false`. (If this is 404 then the door is open.)
         */
        response = api.get("http://localhost:8004/empty/open");
        System.out.println(response.statusCode); // 200 or 404
        System.out.println(response.contentType); // content-type=[application/json]
        System.out.println(response.jsonBody); // true or false
        // findings = checkers.check(response);
        // System.out.println(findings);

        response = api.get("http://localhost:8004/minimal/open");
        // findings = checkers.check(response);
        // System.out.println(findings);

/*
 To walk around use:

    post localhost:8004/empty/walk?row=3&column=5

sends the new coordinate to the room which has to update its internal
representation, so the `@` is in the right place.
 */

        System.exit(0);
    }

    //

    /*
    2. Tester für Clients selber laufen lassen, als Java Tool JAR bzw. Docker
  . most mistakes were:
    . movement broken means not working
      . not moving at all
      . wrong direction on arrows
     */

}
