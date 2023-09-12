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
        self._previous_stats = None

    def start(self):
        self._is_running = True
        self._previous_stats = None
        self.game_service.reset_stats()
        self.ui.mainloop()

    def tick(self):
        if self._is_running:
            self._get_room()
            self._get_stats()
            self._check_if_door_is_open()

    def move_player_to(self, col, row):
        response_json = self.game_service.move(col, row)
        message = self.extract_message(response_json)
        if response_json is not None:
            self.ui.log(f"You moved. The room reponds with: '{message}'")
        else:
            self.ui.log(f"You moved. The room stays silent...")

    def do_action(self, item: Item | None):
        self.ui.log(f"")
        response_json = self.game_service.act(item)
        message = self.extract_message(response_json)
        if response_json is not None:
            self.ui.log(f"You interacted with {item.identifier}... {message}.")
        else:
            self.ui.log(
                f"You interacted with {item.identifier}. The room stays silent..."
            )

    def extract_message(self, response_json):
        message = (
            response_json.get("message", "")
            if isinstance(response_json, dict)
            else response_json
        )
        return message

    def exit_room(self):
        self.ui.log(f"You escaped from the room!")
        won = self.endpoints.next_room()
        if won:
            self._is_running = False
            self.ui.log(f"Congratulations! You've won the Coderetreat :-)")
            self.ui.display_win_screen()
        self.ui.reset()

    def _get_room(self):
        room_json = self.game_service.get_room()
        if room_json:
            room = RoomParser().parse(room_json.get("layout", ""))
            self.ui.update_room(room)
            self.ui.update_room_decription(room_json.get("description", ""))

    def _get_stats(self):
        stats_json = self.game_service.get_stats()

        if self._previous_stats is not None:
            if str(self._previous_stats) != str(stats_json):
                self.ui.log("Look! Your HP!")

        if stats_json is not None:
            if stats_json.get("alive"):
                self.ui.update_stats(stats_json)
            else:
                self.ui.log("You've dieded.")
                self.ui.die()

        self._previous_stats = stats_json

    def _check_if_door_is_open(self):
        is_door_open = self.game_service.open()
        if is_door_open == True:
            self.ui.open_door()
        elif is_door_open == False:
            self.ui.close_door()
        else:
            self.ui.open_door()
