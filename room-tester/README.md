# Rogue-Room-Tester

This is a Java 8 command line tool to test your Roguelike rooms.

## Usage

Make sure your room is running and all required dependencies run as well, e.g. inventory. Let's assume you use the sample room `http://localhost:8004/key`. To check that the room fulfils the API run:

    mvn compile exec:java -Dexec.args="http://localhost:8004/key"

## Docker Image

The application is published on [docker hub](https://hub.docker.com/r/codecop/rogue-room-tester). Run it:

    docker run "codecop/rogue-room-tester" <room URL>
