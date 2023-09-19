import tkinter as tk
from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from src.game.game import Game

from src.ui import log_ui, room_description_ui, room_ui, stats_ui


class UI:
    tile_size = 50

    def __init__(self, game: "Game"):
        self._game = game
        self.window = tk.Tk()

        self._initWindowGeometry(self.window)

        self._room_ui = room_ui.RoomUI(
            self.window,
            self._move_player,
            self._do_action,
            self._exit_room,
        )
        self._stats_ui = stats_ui.StatsUI(self.window)
        self._room_decription_ui = room_description_ui.RoomDescriptionUI(self.window)
        self._log_ui = log_ui.LogUI(self.window)

        self._room_ui.grid(row=0, column=0, sticky="nsew", rowspan=2)
        self._room_decription_ui.grid(row=0, column=1, sticky="new")
        self._stats_ui.grid(row=1, column=1, sticky="new")
        self._log_ui.grid(row=2, column=0, sticky="nsew")

        self.window.bind("<space>", self._exit_room)
        self.window.bind("<Return>", self._restart)

    def reset(self):
        self._room_ui.reset()
        self._room_decription_ui.reset()

    def draw(self):
        self._room_ui.draw()
        self._stats_ui.draw()
        self._room_decription_ui.draw()
        self._log_ui.draw()

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

    def log(self, text: str):
        self._log_ui.add_line(text)

    def display_win_screen(self):
        self._room_decription_ui.win()
        self._stats_ui.update_stats("You live to see the next day...")
        self._room_ui.display_win_screen()

    def mainloop(self):
        self._run()
        self.window.mainloop()

    # def _open_dialog_to_exit_room(self):
    #     dialog = tk.Toplevel(self.window)
    #     dialog.title("Dialog Box")

    #     label = tk.Label(
    #         dialog, text="You escaped this room... Are you ready for the next one?"
    #     )
    #     label.pack(padx=10, pady=10)

    #     go_button = tk.Button(
    #         dialog,
    #         text="Yes! Go to the next room.",
    #         command=lambda: self._exit_room(dialog),
    #     )
    #     go_button.pack(padx=10, pady=10)

    def _run(self):
        self.draw()
        self.window.after(200, self._run)

    def _initWindowGeometry(self, window: tk.Tk):
        window.geometry(
            f"{25*UI.tile_size +  + UI.tile_size}x{25*UI.tile_size +  + UI.tile_size}"
        )
        self.window.grid_rowconfigure(0, weight=1, pad=0)
        self.window.grid_rowconfigure(1, weight=1, pad=0)
        self.window.grid_rowconfigure(2, weight=1, pad=0)
        self.window.grid_columnconfigure(0, weight=1, pad=0)
        self.window.grid_columnconfigure(1, weight=1, pad=0)

    def _move_player(self, col, row):
        self._game.move_player_to(col, row)

    def _do_action(self, *args):
        self._game.do_action(*args)

    def _exit_room(self, *args):
        self._game.exit_room()

    def _restart(self, *args):
        self._game.restart()
