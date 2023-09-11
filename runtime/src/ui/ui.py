import tkinter as tk

from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from src.game.game import Game

from src.ui import room_description_ui, room_ui, stats_ui


class UI:
    tile_size = 50

    def __init__(self, game: "Game"):
        self._game = game
        self.window = tk.Tk()
        self._initWindowGeometry(self.window)

        self._room_ui = room_ui.RoomUi(self.window, self._move_player, self._do_action)
        self._stats_ui = stats_ui.StatsUI(self.window)
        self._room_decription_ui = room_description_ui.RoomDescriptionUI(self.window)

    def draw(self):
        self._room_ui.draw()
        self._stats_ui.draw()
        self._room_decription_ui.draw()

    def update_room(self, room):
        self._room_ui.update_room(room)

    def update_stats(self, stats):
        self._stats_ui.update_stats(stats)

    def mainloop(self):
        self.window.mainloop()

    def _initWindowGeometry(self, window: tk.Tk):
        window.geometry(
            f"{25*UI.tile_size +  + UI.tile_size}x{25*UI.tile_size +  + UI.tile_size}"
        )

    def _move_player(self, col, row):
        self._game.move_player_to(col, row)

    def _do_action(self, *args):
        self._game.do_action(*args)
