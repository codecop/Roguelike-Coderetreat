import unittest
from src.room_parser.building_blocks.door import Door
from src.room_parser.building_blocks.player import Player

from src.room_parser.building_blocks.wall import Wall
from src.room_parser.room_parser import RoomParser


class RoomParserTest(unittest.TestCase):
    def test_parse_room(self):
        room_parser = RoomParser()

        room = room_parser.parse(
            """
    ###
    #@|
    ###
"""
        )
        self.assertListEqual(
            [
                [Wall(), Wall(), Wall()],
                [Wall(), Player(), Door()],
                [Wall(), Wall(), Wall()],
            ],
            room,
        )
