import tkinter as tk


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
        self.frame.grid(row=0, column=1)

        self.text = tk.Label(
            self.frame,
            text="Waiting for stats...",
            padx=10,
            pady=10,
        )
        self.text.pack(fill=tk.BOTH, expand=True)

        self.text.bind("<Configure>", self._update_frame_height)

        self._update_frame_height()

    def draw(self):
        self.text.config(text=str(self.stats))

    def update_stats(self, stats: dict):
        self.stats = stats

    def _update_frame_height(self, *args):
        frame_height = self.text.winfo_reqheight() + 20
        self.frame.config(height=frame_height)
