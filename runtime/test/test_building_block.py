import unittest

from src.building_blocks.create_building_block_from_string import (
    create_building_block_from_string,
)
from src.building_blocks.door import Door
from src.building_blocks.player import Player
from src.building_blocks.wall import Wall
from src.building_blocks.empty import Empty


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


if __name__ == "__main__":
    unittest.main()
