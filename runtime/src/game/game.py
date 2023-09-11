from src.endpoints.endpoints import Endpoints
from src.room_parser.building_blocks.item import Item

from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI
from src.game_service.game_service import GameService


class Game:
    def __init__(self):
        self.ui = UI(self)
        self.endpoints = Endpoints()
        self.game_service = GameService(self.endpoints)

    def tick(self):
        self._get_room()
        self._get_stats()
        self._check_if_door_is_open()

    def _get_room(self):
        room_json = self.game_service.get_room()
        if room_json:
            room = RoomParser().parse(room_json["layout"])
            self.ui.update_room(room)
            self.ui.draw()

    def _get_stats(self):
        stats_json = self.game_service.get_stats()
        self.ui.update_stats(stats_json)
        self.ui.draw()

    def move_player_to(self, col, row):
        response_json = self.game_service.move(col, row)
        if response_json is not None:
            print("Move response: ", str(response_json))
        else:
            print("Response does not contain JSON data")

    def do_action(self, item: Item | None):
        response_json = self.game_service.act(item)
        if response_json is not None:
            print("Interact response: ", str(response_json))
        else:
            print("Response does not contain JSON data")

    def _check_if_door_is_open(self):
        is_door_open = self.game_service.open()
        if is_door_open:
            self.ui.open_door()
        else:
            self.ui.close_door()

    def exit_room(self):
        print("Exiting room")
        exit()

    def mainloop(self):
        self.ui.mainloop()
