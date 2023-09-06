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

    get localhost:8004/1/

returns JSON body with

    { 
      "description": "You are in a little square room. There is nothing here.",
      "layout": "#######\n#  @  #\n#     #\n#     |\n#     #\n#     #\n#######\n"
    }

Check if the door is open or locked:

    get http://localhost:8004/1/open

returns `true` or `false`. To walk around use:

    post localhost:8004/1/walk?row=3&column=5

sends the new coordinate to the room which has to update its internal representation, so the `@` is in the right place.

To interact with items:

    post localhost:8004/1/interact?item=c

This returns optional message how the operation went:

    { 
      "message": "You found a key."
    }

There is also a room /2/ with a chest and a key which has a locked door to test.
