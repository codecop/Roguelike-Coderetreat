import tkinter as tk


class RoomDescriptionUI:
    tile_size = 50
    default_text = "Waiting for room description..."

    def __init__(self, window: tk.Tk):
        self.window = window
        self.room_description = RoomDescriptionUI.default_text

        self.frame = tk.Frame(
            self.window,
            width=300,
            height=300,
            highlightbackground="black",
            highlightthickness=2,
        )
        self.frame.pack_propagate(False)  # Disable automatic resizing of the frame

        self.text = tk.Label(
            self.frame,
            text=self.room_description,
            padx=10,
            pady=10,
        )
        self.text.pack(fill=tk.BOTH, expand=True)

        self.text.bind("<Configure>", self._update_frame_height)

        self._update_frame_height()

    def grid(self, *args, **kwargs):
        self.frame.grid(*args, **kwargs)

    def reset(self):
        self.room_description = RoomDescriptionUI.default_text

    def draw(self):
        self.text.config(text=str(self.room_description))

    def win(self):
        self.update_room_decription(
            "This is a room where you can place your big medal."
        )

    def update_room_decription(self, room_description: str):
        self.room_description = room_description

    def _update_frame_height(self, *args):
        frame_height = self.text.winfo_reqheight() + 20
        self.frame.config(height=frame_height)
