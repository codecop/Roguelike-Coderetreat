class Door:
    def __repr__(self):
        return "D"

    def __eq__(self, other):
        return isinstance(other, Door)
