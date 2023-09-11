import tkinter as tk
from PIL import Image, ImageTk

from src.room_parser.building_blocks.door import Door
from src.room_parser.building_blocks.item import Item
from src.room_parser.building_blocks.wall import Wall
from src.room_parser.building_blocks.empty import Empty
from src.room_parser.building_blocks.player import Player


def tile_pos(col, row):
    pos_y = col * RoomUi.tile_size
    pos_x = row * RoomUi.tile_size
    return (pos_x, pos_y)


class RoomUi:
    tile_size = 50

    def __init__(self, window: tk.Tk, move_player, do_action, exit_room):
        self.window = window

        self._move_player = move_player
        self._do_action = do_action
        self._exit_room = exit_room

        self.canvas = tk.Canvas(
            self.window,
            width=15 * RoomUi.tile_size + RoomUi.tile_size + RoomUi.tile_size,
            height=15 * RoomUi.tile_size + RoomUi.tile_size + RoomUi.tile_size,
            bg="white",
        )

        self.canvas.create_text(
            200,
            200,
            text="Waiting for a room to be provided...",
        )

        self.player_img_src = self._createTkImage("gfx/player.png")
        self.wall_img_src = self._createTkImage("gfx/wall.png")
        self.door_img_src = self._createTkImage("gfx/door-open.png")
        self.door_closed_img_src = self._createTkImage("gfx/door.png")

        self.canvas.grid(row=0, column=0)

        self._is_door_open = False
        self.room = None

        self._bindKeys()

    def update_room(self, room):
        self.room = room

    def open_door(self):
        self._is_door_open = True

    def close_door(self):
        self._is_door_open = False

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
                        pos[0],
                        pos[1],
                        anchor=tk.NW,
                        image=self.door_img_src
                        if self._is_door_open
                        else self.door_closed_img_src,
                    )
                if isinstance(block, Item):
                    self.canvas.create_text(
                        pos[0] + RoomUi.tile_size // 2,
                        pos[1] + RoomUi.tile_size // 2,
                        anchor=tk.CENTER,
                        text=block.identifier,
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

    def _move(self, dCol: int, dRow: int):
        player_col, player_row = self._get_player_position()
        new_player_col, new_player_row = player_col + dCol, player_row + dRow
        if self._tile_is_empty(new_player_col, new_player_row):
            self._draw_and_move_player(
                player_col, player_row, new_player_col, new_player_row
            )

        if self._tile_is_open_door(new_player_col, new_player_row):
            self._exit_room()

    def _tile_is_empty(self, new_player_col, new_player_row):
        return isinstance(self.room[new_player_row][new_player_col], Empty)

    def _tile_is_open_door(self, new_player_col, new_player_row):
        return (
            isinstance(self.room[new_player_row][new_player_col], Door)
            and self._is_door_open
        )

    def _draw_and_move_player(
        self, player_col, player_row, new_player_col, new_player_row
    ):
        self.room[new_player_row][new_player_col] = self.room[player_row][player_col]
        self.room[player_row][player_col] = Empty()
        self.draw()
        self._move_player(new_player_col, new_player_row)

    def _act(self, *args):
        player_col, player_row = self._get_player_position()

        n = (0, -1)
        ne = (1, -1)
        e = (1, 0)
        se = (1, 1)
        s = (0, 1)
        sw = (-1, 1)
        w = (-1, 0)
        nw = (-1, -1)

        item_to_act_on = None
        for direction in [n, ne, e, se, s, sw, w, nw]:
            d_col, d_row = direction
            block = self._get_block_or_empty(player_col + d_col, player_row + d_row)
            if isinstance(block, Item):
                item_to_act_on = block

        print(item_to_act_on)
        self._do_action(item_to_act_on)

    def _get_block_or_empty(self, col, row):
        try:
            return self.room[row][col]
        except IndexError:
            return Empty()

    def _get_player_position(self):
        player_col, player_row = None, None
        for row in range(len(self.room)):
            for col in range(len(self.room[row])):
                if isinstance(self.room[row][col], Player):
                    player_col, player_row = col, row
        return player_col, player_row

    def _bindKeys(self):
        self.window.bind("<Left>", self._left)
        self.window.bind("<Right>", self._right)
        self.window.bind("<Up>", self._up)
        self.window.bind("<Down>", self._down)
        self.window.bind("<space>", self._act)
