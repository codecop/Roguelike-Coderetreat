import random
from src.endpoints.endpoints import Endpoints
from src.game.play_sound import play_beep
from src.room_parser.building_blocks.item import Item

from src.room_parser.room_parser import RoomParseException, RoomParser
from src.ui.ui import UI
from src.game_service.game_service import GameService


class Game:
    def __init__(self, endpoints: Endpoints):
        self.endpoints = endpoints

        self.ui = UI(self)
        self.game_service = GameService(self.endpoints)

        self._is_running = False
        self._previous_stats = None
        self._last_log_type = None

    def start(self):
        self._is_running = True
        self._previous_stats = None
        self.game_service.reset_stats()
        self.ui.mainloop()

    def restart(self):
        self._log_to_ui(f"Restart - let's try one more time.", "restart1")
        self._log_to_ui(f"----------------------------------", "restart2")

        self._is_running = True
        self._previous_stats = None
        self.game_service.reset_stats()
        self.ui.reset()
        self.endpoints.restart()

    def tick(self):
        if self._is_running:
            self._get_room()
            self._get_stats()
            self._check_if_door_is_open()

    def move_player_to(self, col, row):
        response_json = self.game_service.move(col, row)
        message = self.extract_message(response_json)
        if message:
            self._log_to_ui(f"You moved. The room responds with: '{message}'", "move")
        else:
            self._log_to_ui(f"You moved. The room stays silent...", "move_silent")

    def do_action(self, item):
        interact_words = ["bash", "probe", "enchant", "inspect", "interact with"]
        random.shuffle(interact_words)
        response_json = self.game_service.act(item)
        message = self.extract_message(response_json)
        if response_json is not None:
            self._log_to_ui(
                f"You {interact_words[0]} {item.identifier}... {message}.", "interact"
            )
        else:
            self._log_to_ui(
                f"You {interact_words[0]} {item.identifier}. The room stays silent...",
                "interact",
            )

    def extract_message(self, response_json):
        message = (
            response_json.get("message", "")
            if isinstance(response_json, dict)
            else response_json
        )
        return message

    def exit_room(self):
        self._log_to_ui(f"You escaped from the room!", "escape")
        won = self.endpoints.next_room()
        if won:
            self._is_running = False
            self._log_to_ui(f"Congratulations! You've won the Dungeon Crawler :-)", "win")
            self.ui.display_win_screen()
        else:
            self.ui.reset()

    def _log_to_ui(self, text: str, type=None):
        always_log = type in ["hp_change", "move", "interact"]
        is_repetitive = type == self._last_log_type
        should_not_log = is_repetitive and not always_log
        if should_not_log:
            return
        self.ui.log(text)
        self._last_log_type = type

    def _get_room(self):
        room_json = self.game_service.get_room()
        if room_json:
            try:
                room = RoomParser().parse(room_json.get("layout", ""))
                self.ui.update_room(room)
                self.ui.update_room_description(room_json.get("description", ""))
            except RoomParseException as e:
                self.ui.update_room(None)
                self.ui.update_room_description(str(e))

    def _get_stats(self):
        stats_json = self.game_service.get_stats()

        if self._previous_stats is not None:
            if str(self._previous_stats) != str(stats_json):
                play_beep()
                self._log_to_ui("Look! Your HP!", "hp_change")

        if stats_json is not None:
            if stats_json.get("alive"):
                self.ui.update_stats(stats_json)
            else:
                self.ui.update_stats(stats_json)
                self._log_to_ui("You are dead.", "dead")
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
