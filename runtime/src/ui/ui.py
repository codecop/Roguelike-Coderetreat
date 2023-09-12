import threading
import time
import tkinter as tk
from PIL import Image, ImageTk
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

        self._room_ui = room_ui.RoomUi(
            self.window,
            self._move_player,
            self._do_action,
            self._open_dialog_to_exit_room,
        )
        self._stats_ui = stats_ui.StatsUI(self.window)
        self._room_decription_ui = room_description_ui.RoomDescriptionUI(self.window)

    def reset(self):
        self._room_ui.reset()
        self._room_decription_ui.reset()

    def draw(self):
        self._room_ui.draw()
        self._stats_ui.draw()
        self._room_decription_ui.draw()

    def update_room(self, room):
        self._room_ui.update_room(room)

    def update_stats(self, stats):
        self._stats_ui.update_stats(stats)

    def update_room_decription(self, room_description: str):
        self._room_decription_ui.update_room_decription(room_description)

    def open_door(self):
        self._room_ui.open_door()

    def close_door(self):
        self._room_ui.close_door()

    def die(self):
        self._room_ui.die()

    def mainloop(self):
        self._run()
        self.window.mainloop()

    def _open_dialog_to_exit_room(self):
        dialog = tk.Toplevel(self.window)
        dialog.title("Dialog Box")

        label = tk.Label(
            dialog, text="You escaped this room... Are you ready for the next one?"
        )
        label.pack(padx=10, pady=10)

        go_button = tk.Button(
            dialog,
            text="Yes! Go to the next room.",
            command=lambda: self._exit_room(dialog),
        )
        go_button.pack(padx=10, pady=10)

    def _run(self):
        self.draw()
        self.window.after(200, self.run)

    def _initWindowGeometry(self, window: tk.Tk):
        window.geometry(
            f"{25*UI.tile_size +  + UI.tile_size}x{25*UI.tile_size +  + UI.tile_size}"
        )

    def _move_player(self, col, row):
        self._game.move_player_to(col, row)

    def _do_action(self, *args):
        self._game.do_action(*args)

    def _exit_room(self, dialog):
        dialog.destroy()
        self._game.exit_room()
