doRelativeFile("../../io/main.io")
HttpClient

HelloAppTest := UnitTest clone do(

    setUp := method(
        # app @run
        nil
    )

    tearDown := method(
        # TODO app stop
        nil
    )

    test_first_hello := method(
        json := getHello parseJson # TODO remove dependency?
        name := json at("name")
        assertEquals("World!", name)
    )

    getHello := method(
        request := self helloRequest
        request setHttpMethod("GET")

        response := request connection sendRequest response

        assertEquals("200", response statusCode)
        assertEquals("application/json", response headerAt("Content-Type"))

        response content
    )

    helloRequest := method(
        url := HCUrl with("http://localhost:#{PORT}/hello" interpolate)
        request := HCRequest with(url)
        request setHeader("Accept", "application/json")
        request
    )

    test_z_update := method(
        request := self helloRequest
        request setHttpMethod("POST")
        request setBody("{\"name\":\"Peter\"}")
        request setHeader("Content-Type", "application/json")

        response := request connection sendRequest response

        assertEquals("201", response statusCode)
        assertEquals("application/json", response headerAt("Content-Type"))

        json := getHello parseJson # TODO remove dependency?
        name := json at("name")
        assertEquals("Peter", name)
    )

)
