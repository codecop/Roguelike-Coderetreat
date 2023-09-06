# Rogue-Room-sample 8003

This is a Java 8 [Maven](https://maven.apache.org/) project.

Uses [Micronaut](https://micronaut.io/).

The project uses [JUnit 5](https://junit.org/junit5/) for testing.

To run the tests:

    mvn test

To start the application:

    mvn mn:run

## Room Setup

This is a setup which needs more logic on the room's side. There are two end-points:

* get the room
* send an action

The room has to keep track of player coordinates, if a player can walk into a direction and so on.

## Progress and Times

Minimal functionality, 5 tests and 40' for me = 2 sessions for people.

* description and layout display
* player moves
* player does not move into walls
* player exits at door

Integration using template, 2 tests and 15' for me = 1 sessions for people.

Chest/item functionality, 4+ tests and 45' for me = 2 sessions for people. that is max.

* door is locked, can not exit
* there is a chest
* search next to chest (to find key)
* search next to pick up key
* door is unlocked

## Usage

Get the room:

    get localhost:8003/1/

returns JSON body with

    { 
      "description": "You are in a little square room. There is nothing here.",
      "layout": "#######\n#  @  #\n#     #\n#     |\n#     #\n#     #\n#######\n"
    }

Perform actions:

    post localhost:8003/1/?action=[wasd ]

to move around ('wasd') and search (' ') and returns optional message how the operation went:

    { 
      "message": "The door is locked."
    }

There is also a room /2/ with a chest and a key which has a locked door and returns messages.
