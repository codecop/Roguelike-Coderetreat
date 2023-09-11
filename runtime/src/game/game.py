import requests
from src.endpoint_provider.endpoint_provider import EndpointProvider
from src.room_parser.building_blocks.item import Item

from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI


class Game:
    stats = {}

    def __init__(self):
        self.ui = UI(self)
        self.current_room_index = 0
        self.endpoint_provider = EndpointProvider()

    def update_room(self, room_json):
        room = RoomParser().parse(room_json["layout"])
        self.ui.update_room(room)
        self.ui.draw()

    def update_stats(self, stats):
        self.stats = stats
        self.ui.update_stats(stats)
        self.ui.draw()

    def move_player_to(self, col, row):
        response = requests.post(f"{self.room_url}/walk?column={col}&row={row}")
        if "application/json" in response.headers.get("Content-Type", ""):
            print("Move response: ", str(response.json()))
        else:
            print("Response does not contain JSON data")

    def do_action(self, item: Item | None):
        print("Action on", item.identifier if item is not None else None)

    @property
    def room_url(self):
        return self.endpoint_provider.rooms_endpoints[self.current_room_index]

    def next_room(self):
        rooms_count = len(self.endpoint_provider.rooms_endpoints)
        self.current_room_index = (self.current_room_index + 1) % rooms_count

    def mainloop(self):
        self.ui.mainloop()
