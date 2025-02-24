Hello := Object clone do(

    init := method(
        self name := "World!"
    )

    getName := method(
        self name
    )

    setName := method(name,
        self name = name
    )

    asJson := method(
        Map with("name", name) asJson
    )

    // nameFromJson := method(json,
    //     Matcher matcher := Pattern compile("\"name\":\\s*\"([^\"]+)\"") matcher(json)
    //     if (!matcher find,
    //          return false
    //     )
    //     setName(matcher group(1))
    //     return false
    // )

)
