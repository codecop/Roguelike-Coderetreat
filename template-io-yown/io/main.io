#!/usr/bin/env io

PORT := 8010

main := Yown clone do(

    WebServer setPort(PORT)

    hello := Hello clone // mutable data, not concurrent, not thread safe

    get("/hello",
        // res.type("application/json");
        hello asJson
    )

    post("/hello",
            // String body = req.body();
            // if (req.contentType().startsWith("application/json") && hello.nameFromJson(body)) {
            //     res.status(HttpServletResponse.SC_CREATED);
            // } else {
            //     res.status(HttpServletResponse.SC_BAD_REQUEST);
            // }
            // res.type("application/json");
            ""
    )
)

if (isLaunchScript,
    "Hello started on #{PORT},\nOpen http://localhost:#{PORT}/hello" interpolate println
    main run
)
