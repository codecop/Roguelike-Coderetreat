# Rogue-Stats @ 8002

This is a Node [npm](https://www.npmjs.com/) project.

Uses [Express minimalist web framework for Node.js](http://expressjs.com/).

To install dependencies:

    npm install

The project uses [Mocha](https://mochajs.org/), [Chai](https://www.chaijs.com/) and [SuperTest](https://github.com/visionmedia/supertest) for testing.

To run the tests:

    npm test

To start the application:

    npm start

## Docker Image

The application is published on [docker hub](https://hub.docker.com/r/codecop/rogue-stats). Run it:

    docker run -p8002:8002 "codecop/rogue-stats"

## Usage

Basic element of stats is HP in range 0 to 10:

    get localhost:8002/stats/hp

returns JSON body with

    { "hp": 10, "alive": true }

To update the HP:

    post to localhost:8002/stats/hp?action=reset
    post to localhost:8002/stats/hp?action=hit
    post to localhost:8002/stats/hp?action=heal

to reset, decrease of increase the HP during a game.

There are dynamic stats, e.g. a level counter

    post to localhost:8002/stats/level?action=inc
    post to localhost:8002/stats/level?action=dec

and after the first write, it is available to query

    get localhost:8002/stats/level

with JSON body

    { "level": 1 }

otherwise it is 404.

To remove the dynamic counter again:

    delete localhost:8002/stats/level
