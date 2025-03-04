doRelativeFile("../../io/main.io")
HttpClient

HelloAppTest := UnitTest clone do(

    test_a_setUp := method(
        // trick a one time setup which is run first as tests are sorted by line number
        app @run
    )

    test_first_hello := method(
        json := getHello
        assertEquals("{\"name\":\"World!\"}", json)
    )

    getHello := method(
        // wget http://localhost:8010/hello
        request := self helloRequest("")
        request setHttpMethod("GET")

        response := request connection sendRequest response

        assertEquals("200", response statusCode)
        assertEquals("application/json", response headerAt("Content-Type"))

        response content
    )

    helloRequest := method(params,
        url := HCUrl with("http://localhost:#{PORT}/hello#{params}" interpolate)
        request := HCRequest with(url)
        request setHeader("Accept", "application/json")
        request
    )

    test_y_update := method(
        // wget --method POST http://localhost:8010/hello?name=Peter
        request := self helloRequest("?name=Peter")
        request setHttpMethod("POST")
        request setBody("")

        response := request connection sendRequest response

        assertEquals("201", response statusCode)
        assertEquals("application/json", response headerAt("Content-Type"))

        json := getHello
        assertEquals("{\"name\":\"Peter\"}", json)
    )

    test_t_tearDown := method(
        app stop
    )

)
