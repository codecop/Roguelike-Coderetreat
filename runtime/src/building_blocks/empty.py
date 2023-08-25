class Empty:
    def __repr__(self):
        return "E"

    def __eq__(self, other):
        return isinstance(other, Empty)
