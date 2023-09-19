
class Hello:

    name = "World!"

    def getName(self) -> str: 
        return self.name
    
    def setName(self, name: str) -> str:
        self.name = name