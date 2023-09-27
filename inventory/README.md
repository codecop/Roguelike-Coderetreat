# Rogue-Inventory @ 8001

This is a Java 8 [Maven](https://maven.apache.org/) project.

Uses [Spark micro framework for creating web applications](https://sparkjava.com/).

The project uses [JUnit 5](https://junit.org/junit5/) and [REST assured](http://rest-assured.io/) for testing.

To run the tests:

    mvn test

To start the application:

    mvn compile exec:java

## Docker Image

The application is published on [docker hub](https://hub.docker.com/r/codecop/rogue-inventory). Run it:

    docker run -p8001:8001 "codecop/rogue-inventory"

## Usage

List the whole inventory:

    get localhost:8001/inventory

returns JSON body with

    [
      { "item": "(", "description": "a bow" },
      { "item": "k", "description": "Master key" }
    ]

Check for a specific item, e.g. only open door if key is there:

    get localhost:8001/inventory/k

returns

    { "item": "k", "description": "Master key" }

or 404.

To add a new item to the inventory:

    post { "item": "-", "description": "little knife" } to localhost:8001/inventory

And

    delete localhost:8001/inventory/k

removes the item from the inventory, e.g. if it has been used.

To clear the whole inventory:

    delete localhost:8001/inventory
