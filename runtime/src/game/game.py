from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI


class Game:
    def __init__(self):
        self.ui = UI()

    def update(self, room_string: str):
        room = RoomParser().parse(room_string)
        self.ui.draw(room)

    def mainloop(self):
        self.ui.mainloop()
