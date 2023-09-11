import requests

from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI


class Game:
    stats = {}

    def __init__(self):
        self.ui = UI(self)

    def update_room(self, room_json):
        room = RoomParser().parse(room_json["layout"])

        print(room_json["description"])

        self.ui.update_room(room)
        self.ui.draw()

    def update_stats(self, stats):
        self.stats = stats
        self.ui.update_stats(stats)
        self.ui.draw()

    def move_player_to(self, col, row):
        response = requests.post(f"http://localhost:8003/1/walk?column={col}&row={row}")
        print("Move response: ", str(response.json()))

    def do_action(self, *args):
        print("Action")

    def mainloop(self):
        self.ui.mainloop()
