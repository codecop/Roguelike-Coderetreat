#!/usr/bin/env io
doRelativeFile("./Hello.io")

PORT := 8010

app := Yown clone do(
    // `self` is the `app`, with prototype of `Yown`,
    // which has a `req` with prototype of `Yown WebRequest`.
    // `Yown WebServer` has prototype of (Socket's) ` Server`
    // which has an `app` field with prototype of `Yown`, and the `run` method.

    Yown WebServer setPort(PORT)

    hello := Hello clone // mutable data, not concurrent, not thread safe

    get("/hello",
        req sendHeader ("Content-type", "application/json")

        // req queryPath -> the full path
        // req queryArgs -> a map of string for each http parameter

        hello asJson
    )

    post("/hello",
        req sendHeader ("Content-type", "application/json")

        // req queryArgs -> a map of post parameters
        body := req queryArgs at("body")

        # if (req headers at("Content-type") ?beginSeq("application/json"),
        e := try(
            json := body parseJson
        )

        if (e not,
            hello setName(json at("name"))
            req sendResponse (201, "CREATED")
            ""
        ,
            req sendResponse (400, "BAD_REQUEST")
            e showStack
        )

        ""
    )

    stop := method(
        Yown WebServer stop
    )
)

if (isLaunchScript,
    "Hello started on #{PORT},\nOpen http://localhost:#{PORT}/hello" interpolate println
    app run
)
