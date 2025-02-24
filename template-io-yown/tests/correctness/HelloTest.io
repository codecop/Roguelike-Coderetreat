doRelativeFile("../../io/Hello.io")

HelloTest := UnitTest clone do(

    test_get := method(
        hello := Hello clone
        assertEquals("World!", hello getName)
    )

)
