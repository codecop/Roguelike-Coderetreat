# Rogue-Room-sample 8004

This is a Java 8 [Maven](https://maven.apache.org/) project.

Uses [Micronaut](https://micronaut.io/).

The project uses [JUnit 5](https://junit.org/junit5/) for testing.

To run the tests:

    mvn test

To start the application:

    mvn mn:run

## Room Setup

This is a setup which needs less logic on the room's side. There are multiple end-points:

* get the room
* walk: send a movement (new coordinates)
* can exit: is the door open?
* interaction: send an interaction with an item

The room has to keep track of player coordinates, if a player can walk into a direction and so on.

## Progress and Times

tbd

## Usage

Get the room:

    get localhost:8004/empty/

returns JSON body with

    { 
      "description": "You are in a little square room. There is nothing here.",
      "layout": "#######\n#  @  #\n#     #\n#     |\n#     #\n#     #\n#######\n"
    }

(Description is optional.) Check if the door is open or locked with:

    get http://localhost:8004/empty/open

returns `true` or `false`. (If this is 404 then the door is open.) To walk around use:

    post localhost:8004/empty/walk?row=3&column=5

sends the new coordinate to the room which has to update its internal 
representation, so the `@` is in the right place. To interact with items:

    post localhost:8004/empty/interact?item=c

This returns optional message how the operation went:

    { 
      "message": "You found a key."
    }

(The whole interaction and its message is optional. 404 is OK.)

### Other Sample Rooms

There is a room `/key/` with a chest to interact and a key which has a locked door to test. This is a nice room which can be demo-ed.

The room `/minimal/` is the smallest implementation, only layout and walk(ing). Other end-points are 404.

Room `/large/` has a 50x50 grid, the door is in the middle on the right.

Room `/monster/` has a zombie which is chasing and killing the player (using `/stats/` service). It moves towards the player and hits it every 3 ticks. This is a nice room which can be demo-ed.
