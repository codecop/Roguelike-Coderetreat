import unittest

from src.building_blocks.create_building_block_from_string import (
    createBuildingBlockFromString,
)
from src.building_blocks.door import Door
from src.building_blocks.player import Player
from src.building_blocks.wall import Wall


class BuildingBlockTest(unittest.TestCase):
    def test_wall_building_block(self):
        raw = "#"
        builing_block = createBuildingBlockFromString(raw)
        self.assertIsInstance(builing_block, Wall)

    def test_door_building_block(self):
        raw = "|"
        builing_block = createBuildingBlockFromString(raw)
        self.assertIsInstance(builing_block, Door)

    def test_player_building_block(self):
        raw = "@"
        builing_block = createBuildingBlockFromString(raw)
        self.assertIsInstance(builing_block, Player)


if __name__ == "__main__":
    unittest.main()
