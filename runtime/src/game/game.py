import requests

from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI


class Game:
    def __init__(self):
        self.ui = UI(self)

    def update(self, room_string: str):
        room = RoomParser().parse(room_string)
        self.ui.set_room(room)
        self.ui.draw()

    def mainloop(self):
        self.ui.mainloop()

    def move(self, col, row):
        requests.post("http://127.0.0.1/move", data={"col": col, "row": row})
