import tkinter as tk
from PIL import Image, ImageTk

from src.building_blocks.door import Door
from src.building_blocks.wall import Wall
from src.building_blocks.empty import Empty

from src.building_blocks.player import Player


def tile_pos(col, row):
    pos_y = col * UI.tile_size
    pos_x = row * UI.tile_size
    return (pos_x, pos_y)


class UI:
    tile_size = 50

    def __init__(self, game):
        self._game = game

        self.window = tk.Tk()
        self._initWindowGeometry(self.window)

        self.canvas = tk.Canvas(
            self.window,
            width=15 * UI.tile_size + UI.tile_size + UI.tile_size,
            height=15 * UI.tile_size + UI.tile_size + UI.tile_size,
            bg="white",
        )

        self.canvas.create_text(
            200,
            200,
            text="Waiting for a room to be provided...",
        )

        self.player_img_src = self._createTkImage("gfx/player.png")
        self.wall_img_src = self._createTkImage("gfx/wall.png")
        self.door_img_src = self._createTkImage("gfx/door.png")
        self.canvas.pack(pady=20)

        ## Showing stats

        # Function to update the frame height based on text content
        def update_frame_height():
            frame_height = self.text.winfo_reqheight() + (2 * 10)
            self.frame.config(height=frame_height)

        # Create a frame with a dynamic height
        self.frame = tk.Frame(self.window, width=300, height=300)
        self.frame.pack_propagate(False)  # Disable automatic resizing of the frame
        self.frame.pack()

        # Create a label with centered text inside the frame
        self.text = tk.Label(
            self.frame,
            text=str(self._game.stats),
            padx=10,
            pady=10,
            highlightbackground="black",
            highlightthickness=2,
        )
        self.text.pack(
            fill=tk.BOTH, expand=True
        )  # Center the text and fill the available space

        # Bind the label text update to changes in the label's text
        self.text.bind("<Configure>", lambda event: update_frame_height())

        # Initialize the frame height based on the initial text
        update_frame_height()

        self._bindKeys()

    def set_room(self, room):
        self.room = room

    def draw_stats(self):
        self.text.config(text=str(self._game.stats))

    def draw(self):
        if self.room is None:
            return

        for obj in self.canvas.find_all():
            self.canvas.delete(obj)

        for col_pos, room_row in enumerate(self.room):
            for row_pos, block in enumerate(room_row):
                pos = tile_pos(col_pos, row_pos)
                if isinstance(block, Player):
                    self.player_img = self.canvas.create_image(
                        pos[0], pos[1], anchor=tk.NW, image=self.player_img_src
                    )
                if isinstance(block, Wall):
                    self.canvas.create_image(
                        pos[0], pos[1], anchor=tk.NW, image=self.wall_img_src
                    )
                if isinstance(block, Door):
                    self.canvas.create_image(
                        pos[0], pos[1], anchor=tk.NW, image=self.door_img_src
                    )

    def mainloop(self):
        self.window.mainloop()

    def _initWindowGeometry(self, window: tk.Tk):
        window.geometry(
            f"{25*UI.tile_size +  + UI.tile_size}x{25*UI.tile_size +  + UI.tile_size}"
        )

    def _createTkImage(self, path):
        return ImageTk.PhotoImage(Image.open(path).resize((50, 50)))

    def _left(self, e):
        self._move(-1, 0)

    def _right(self, e):
        self._move(1, 0)

    def _up(self, e):
        self._move(0, -1)

    def _down(self, e):
        self._move(0, 1)

    def _move(self, dCol, dRow):
        player_col, player_row = None, None
        for row in range(len(self.room)):
            for col in range(len(self.room[row])):
                if isinstance(self.room[row][col], Player):
                    player_col, player_row = col, row

        if isinstance(self.room[player_row + dRow][player_col + dCol], Empty):
            self.room[player_row + dRow][player_col + dCol] = self.room[player_row][
                player_col
            ]
            self.room[player_row][player_col] = Empty()
            self.draw()

            self._game.move(col, row)

    def _bindKeys(self):
        self.window.bind("<Left>", self._left)
        self.window.bind("<Right>", self._right)
        self.window.bind("<Up>", self._up)
        self.window.bind("<Down>", self._down)
