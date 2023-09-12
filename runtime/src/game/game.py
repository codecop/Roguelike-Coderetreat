from src.endpoints.endpoints import Endpoints
from src.room_parser.building_blocks.item import Item

from src.room_parser.room_parser import RoomParser
from src.ui.ui import UI
from src.game_service.game_service import GameService


class Game:
    def __init__(self, endpoints: Endpoints):
        self.endpoints = endpoints

        self.ui = UI(self)
        self.game_service = GameService(self.endpoints)

        self._is_running = False

    def tick(self):
        if self._is_running:
            self._get_room()
            self._get_stats()
            self._check_if_door_is_open()
            # self.ui.draw()

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

    def exit_room(self):
        self.endpoints.next_room()
        self.ui.reset()

    def _get_room(self):
        room_json = self.game_service.get_room()
        if room_json:
            room = RoomParser().parse(room_json.get("layout", ""))
            self.ui.update_room(room)
            self.ui.update_room_decription(room_json.get("description", ""))

    def _get_stats(self):
        stats_json = self.game_service.get_stats()
        if stats_json is not None:
            if stats_json.get("alive"):
                self.ui.update_stats(stats_json)
            else:
                self.ui.die()

    def _check_if_door_is_open(self):
        is_door_open = self.game_service.open()
        if is_door_open == True:
            self.ui.open_door()
        elif is_door_open == False:
            self.ui.close_door()
        else:
            self.ui.open_door()

    def start(self):
        self._is_running = True
        self.game_service.reset_stats()
        self.ui.mainloop()
