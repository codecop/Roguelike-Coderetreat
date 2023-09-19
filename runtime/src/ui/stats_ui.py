from re import T
import tkinter as tk
from turtle import st


class StatsUI:
    tile_size = 50

    def __init__(self, window: tk.Tk):
        self.window = window
        self.stats = dict()

        self.frame = tk.Frame(
            self.window,
            width=300,
            height=300,
            highlightbackground="black",
            highlightthickness=2,
        )
        self.frame.pack_propagate(False)  # Disable automatic resizing of the frame

        custom_font = ("Helvetica", 30)
        self.text = tk.Label(
            self.frame,
            text="Waiting for stats...",
            padx=10,
            pady=10,
            font=custom_font,
            fg="black",
        )
        self.text.pack(fill=tk.BOTH, expand=True)

        self.text.bind("<Configure>", self._update_frame_height)

        self._update_frame_height()

    def grid(self, *args, **kwargs):
        self.frame.grid(*args, **kwargs)

    def draw(self):
        color = "black"
        text = str(self.stats)
        hp = text
        if isinstance(self.stats, dict):
            hp = self.stats.get("hp", None)
            if hp is not None:
                text = f"HP: {hp}"

        try:
            int_value = int(hp)
            if int_value >= 5:
                color = "green"
            elif int_value >= 1:
                color = "orange"
            else:
                color = "red"

        except ValueError:
            pass

        self.text.config(text=text, fg=color)

    def update_stats(self, stats: dict):
        self.stats = stats

    def _update_frame_height(self, *args):
        frame_height = self.text.winfo_reqheight() + 20
        self.frame.config(height=frame_height)
