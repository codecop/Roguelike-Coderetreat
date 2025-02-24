#!/usr/bin/env io
doRelativeFile("./Hello.io")

PORT := 8010

app := Yown clone do(
    // self is app, with prototype of Yown, which has req with prototype of WebRequest

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
)

if (isLaunchScript,
    "Hello started on #{PORT},\nOpen http://localhost:#{PORT}/hello" interpolate println
    app run
)
