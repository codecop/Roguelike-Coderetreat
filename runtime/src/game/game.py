import requests

from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI


class Game:
    stats = {}

    def __init__(self):
        self.ui = UI(self)

    def update(self, room_string: str):
        room = RoomParser().parse(room_string)
        self.ui.set_room(room)
        self.ui.draw()

    def mainloop(self):
        self.ui.mainloop()

    def get_stats(self):
        response = requests.get("http://localhost:8002/stats/hp")
        self.stats = response.json()
        print("Updated stats...", self.stats, response.json())
        self.ui.draw_stats()

    def move(self, col, row):
        print("sending col, row", col, row)
        response = requests.post(f"http://localhost:8004/1/walk?column={col}&row={row}")
        print(str(response.json()))
