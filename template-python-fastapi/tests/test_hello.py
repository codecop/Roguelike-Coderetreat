from src.hello import Hello


def test_get_name():
    hello = Hello()
    assert hello.getName() == "World!"
