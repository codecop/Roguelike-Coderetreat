import unittest

from src.room_parser.building_blocks.create_building_block_from_string import (
    create_building_block_from_string,
)
from src.room_parser.building_blocks.door import Door
from src.room_parser.building_blocks.player import Player
from src.room_parser.building_blocks.wall import Wall
from src.room_parser.building_blocks.empty import Empty
from src.room_parser.building_blocks.item import Item


class BuildingBlockTest(unittest.TestCase):
    def test_wall_building_block(self):
        raw = "#"
        builing_block = create_building_block_from_string(raw)
        self.assertIsInstance(builing_block, Wall)

    def test_door_building_block(self):
        raw = "|"
        builing_block = create_building_block_from_string(raw)
        self.assertIsInstance(builing_block, Door)

    def test_player_building_block(self):
        raw = "@"
        builing_block = create_building_block_from_string(raw)
        self.assertIsInstance(builing_block, Player)

    def test_empty_building_block(self):
        raw = " "
        builing_block = create_building_block_from_string(raw)
        self.assertIsInstance(builing_block, Empty)

    def test_item_building_block(self):
        builing_block1 = create_building_block_from_string("a")
        builing_block2 = create_building_block_from_string("1")
        builing_block3 = create_building_block_from_string("z")
        builing_block4 = create_building_block_from_string("c")

        self.assertIsInstance(builing_block1, Item)
        self.assertIsInstance(builing_block2, Item)
        self.assertIsInstance(builing_block3, Item)
        self.assertIsInstance(builing_block4, Item)


if __name__ == "__main__":
    unittest.main()
