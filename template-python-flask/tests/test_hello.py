from src.hello import Hello

def test_get_name():
    # Test the getName method
    hello = Hello()
    assert hello.getName() == "World!"