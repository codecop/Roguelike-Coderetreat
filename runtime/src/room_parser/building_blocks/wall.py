class Wall:
    def __repr__(self):
        return "W"

    def __eq__(self, other):
        return isinstance(other, Wall)
