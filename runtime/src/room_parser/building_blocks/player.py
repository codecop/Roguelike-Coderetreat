class Player:
    def __repr__(self):
        return "P"

    def __eq__(self, other):
        return isinstance(other, Player)
