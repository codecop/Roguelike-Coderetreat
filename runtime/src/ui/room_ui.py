import tkinter as tk
from tkinter.font import ROMAN
from PIL import Image, ImageTk

from src.room_parser.building_blocks.door import Door
from src.room_parser.building_blocks.item import Item
from src.room_parser.building_blocks.wall import Wall
from src.room_parser.building_blocks.empty import Empty
from src.room_parser.building_blocks.player import Player


def tile_pos(col, row):
    pos_y = col * RoomUI.tile_size
    pos_x = row * RoomUI.tile_size
    return (pos_x, pos_y)


class RoomUI:
    tile_size = 50
    width = 15 * 50
    height = 15 * 50

    def __init__(self, window: tk.Tk, move_player, do_action, exit_room):
        self.window = window

        self._move_player = move_player
        self._do_action = do_action
        self._exit_room = exit_room

        self.canvas = tk.Canvas(
            self.window,
            width=RoomUI.width,
            height=RoomUI.height,
            bg="white",
        )

        self.player_img_src = self._createTkImage("gfx/player.png")
        self.dead_img_src = self._createTkImage("gfx/dead.png")
        self.wall_img_src = self._createTkImage("gfx/wall.png")
        self.door_img_src = self._createTkImage("gfx/door-open.png")
        self.door_closed_img_src = self._createTkImage("gfx/door.png")
        self.win_img_src = self._createTkImage(
            "gfx/medal.png", RoomUI.width, RoomUI.height
        )

        self.canvas.grid(row=0, column=0, sticky="W")

        self._room = None
        self._is_door_open = False
        self._is_dead = False
        self._won = False

        self._bindKeys()

    def grid(self, *args, **kwargs):
        self.canvas.grid(*args, **kwargs)

    def reset(self):
        self._room = None
        self._is_door_open = False
        self._is_dead = False
        self._won = False

    def update_room(self, room):
        self._room = room

    def open_door(self):
        self._is_door_open = True

    def close_door(self):
        self._is_door_open = False

    def die(self):
        self._is_dead = True

    def draw(self):
        if self._won:
            return

        self.canvas.delete("all")
        self.canvas.update_idletasks()

        if self._room is None:
            self.canvas.create_text(
                200,
                200,
                text="Waiting for a valid room to be provided...",
            )
            return

        for col_pos, room_row in enumerate(self._room):
            for row_pos, block in enumerate(room_row):
                pos = tile_pos(col_pos, row_pos)
                if isinstance(block, Player):
                    self.player_img = self.canvas.create_image(
                        pos[0],
                        pos[1],
                        anchor=tk.NW,
                        image=self.player_img_src
                        if not self._is_dead
                        else self.dead_img_src,
                    )
                if isinstance(block, Wall):
                    self.canvas.create_image(
                        pos[0], pos[1], anchor=tk.NW, image=self.wall_img_src
                    )
                if isinstance(block, Door):
                    self.canvas.create_image(
                        pos[0],
                        pos[1],
                        anchor=tk.NW,
                        image=self.door_img_src
                        if self._is_door_open
                        else self.door_closed_img_src,
                    )
                if isinstance(block, Item):
                    self.canvas.create_text(
                        pos[0] + RoomUI.tile_size // 2,
                        pos[1] + RoomUI.tile_size // 2,
                        anchor=tk.CENTER,
                        text=block.identifier,
                        font=("Purisa", 20),
                    )

    def display_win_screen(self):
        self._won = True
        self.canvas.delete("all")
        self.canvas.create_image(0, 0, anchor=tk.NW, image=self.win_img_src)

    def _createTkImage(self, path, width=50, height=50):
        return ImageTk.PhotoImage(Image.open(path).resize((width, height)))

    def _left(self, e):
        self._move(-1, 0)

    def _right(self, e):
        self._move(1, 0)

    def _up(self, e):
        self._move(0, -1)

    def _down(self, e):
        self._move(0, 1)

    def _move(self, dCol: int, dRow: int):
        if self._is_dead or self._room is None:
            return

        player_col, player_row = self._get_player_position()
        desired_player_col, desired_player_row = player_col + dCol, player_row + dRow

        if self._tile_is_empty(desired_player_col, desired_player_row):
            self._draw_and_move_player(
                player_col, player_row, desired_player_col, desired_player_row
            )

        elif self._tile_is_open_door(desired_player_col, desired_player_row):
            self._exit_room()

        elif self._tile_is_item(desired_player_col, desired_player_row):
            self._act(self._room[desired_player_row][desired_player_col])

    def _tile_is_empty(self, new_player_col, new_player_row):
        try:
            return isinstance(self._room[new_player_row][new_player_col], Empty)
        except IndexError:
            return False

    def _tile_is_open_door(self, new_player_col, new_player_row):
        return (
            isinstance(self._room[new_player_row][new_player_col], Door)
            and self._is_door_open
        )

    def _tile_is_item(self, new_player_col, new_player_row):
        return isinstance(self._room[new_player_row][new_player_col], Item)

    def _draw_and_move_player(
        self, player_col, player_row, new_player_col, new_player_row
    ):
        self._room[new_player_row][new_player_col] = self._room[player_row][player_col]
        self._room[player_row][player_col] = Empty()
        self.draw()
        self._move_player(new_player_col, new_player_row)

    def _act(self, item: Item):
        self._do_action(item)

    def _get_block_or_empty(self, col, row):
        try:
            return self._room[row][col]
        except IndexError:
            return Empty()

    def _get_player_position(self):
        player_col, player_row = None, None
        for row in range(len(self._room)):
            for col in range(len(self._room[row])):
                if isinstance(self._room[row][col], Player):
                    player_col, player_row = col, row
        return player_col, player_row

    def _bindKeys(self):
        self.window.bind("<Left>", self._left)
        self.window.bind("<Right>", self._right)
        self.window.bind("<Up>", self._up)
        self.window.bind("<Down>", self._down)
