import enum
from re import U
from tkinter import *
from PIL import Image, ImageTk
from src.building_blocks.door import Door
from src.building_blocks.wall import Wall

from src.building_blocks.player import Player


def tile_pos(col, row):
    pos_y = col * UI.tile_size
    pos_x = row * UI.tile_size
    return (pos_x, pos_y)


class UI:
    tile_size = 50

    def __init__(self, room):
        height = len(room)
        width = len(room[0])

        self.win = Tk()
        self.win.geometry(
            f"{width*UI.tile_size +  + UI.tile_size}x{height*UI.tile_size +  + UI.tile_size}"
        )

        self.canvas = Canvas(
            self.win,
            width=width * UI.tile_size + UI.tile_size + UI.tile_size,
            height=height * UI.tile_size + UI.tile_size + UI.tile_size,
            bg="white",
        )
        self.canvas.pack(pady=20)

        self.player_image_source = ImageTk.PhotoImage(
            Image.open("player.png").resize((50, 50))
        )

        self.player_wall_source = ImageTk.PhotoImage(
            Image.open("wall.png").resize((50, 50))
        )

        self.player_door_source = ImageTk.PhotoImage(
            Image.open("door.png").resize((50, 50))
        )

        for col_pos, room_row in enumerate(room):
            for row_pos, block in enumerate(room_row):
                pos = tile_pos(col_pos, row_pos)
                if isinstance(block, Player):
                    self.player_img = self.canvas.create_image(
                        pos[0], pos[1], anchor=NW, image=self.player_image_source
                    )
                if isinstance(block, Wall):
                    self.canvas.create_image(
                        pos[0], pos[1], anchor=NW, image=self.player_wall_source
                    )
                if isinstance(block, Door):
                    self.canvas.create_image(
                        pos[0], pos[1], anchor=NW, image=self.player_door_source
                    )

    def run(self):
        self.bind()
        self.win.mainloop()

    def left(self, e):
        x = -UI.tile_size
        y = 0
        self.canvas.move(self.player_img, x, y)

    def right(self, e):
        x = UI.tile_size
        y = 0
        self.canvas.move(self.player_img, x, y)

    def up(self, e):
        x = 0
        y = -UI.tile_size
        self.canvas.move(self.player_img, x, y)

    def down(self, e):
        x = 0
        y = UI.tile_size
        self.canvas.move(self.player_img, x, y)

    def bind(self):
        self.win.bind("<Left>", self.left)
        self.win.bind("<Right>", self.right)
        self.win.bind("<Up>", self.up)
        self.win.bind("<Down>", self.down)
