class Hello:

    def __init__(self):
        self.name = "World!"

    def getName(self) -> str:
        return self.name

    def setName(self, name: str) -> None:
        self.name = name
