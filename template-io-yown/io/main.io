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
        hello asJson
    )

    get("/test",
        # if (req headers at("Content-type") beginWithSeqSeq("application/json"),
            // && hello.nameFromJson(body)) {
            req sendResponse (201, "CREATED")
            "<pre>" ..
            req queryArgs asJson ..
            "</pre>" ..
            "<pre>" ..
            req headers asJson ..
            "</pre>"
    )

    post("/hello",
        req sendHeader ("Content-type", "application/json")
        if (req headers at("Content-type") beginSeq("application/json"),
            // && hello.nameFromJson(body)) {
            req sendResponse (201, "CREATED")
            "<pre>" ..
            req queryArgs asString ..
            "</pre>"
        ,
            req sendResponse (400, "BAD_REQUEST")
            ""
        )
    )

    stop := method(
        Yown WebServer stop
    )
)

if (isLaunchScript,
    "Hello started on #{PORT},\nOpen http://localhost:#{PORT}/hello" interpolate println
    app run
)
