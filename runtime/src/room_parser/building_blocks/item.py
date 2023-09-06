class Item:
    def __init__(self, identifier: str):
        self.identifier = identifier

    def __repr__(self):
        return self.identifier.upper()

    def __eq__(self, other):
        return isinstance(other, Item)
