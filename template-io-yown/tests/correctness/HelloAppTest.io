doRelativeFile("../../io/main.io")
HttpClient

HelloAppTest := UnitTest clone do(

    setUp := method(
        # app @run
        nil
    )

    tearDown := method(
        # TODO Spark stop
        nil
    )

    testFirstHello := method(
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

    // testzupdate := method(
    //     self helloRequest 
    //         contentType(ContentType JSON) body("( \"name\":\"Peter\" )")  //
    //     when  //
    //         post("/hello")  //
    //     then  //
    //         assertThat statusCode(201)

    //     JsonPath json := getHello
    //     String name := json get("name")
    //     assertEquals("Peter", name)
    // )

)
