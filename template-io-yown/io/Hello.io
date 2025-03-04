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

)
