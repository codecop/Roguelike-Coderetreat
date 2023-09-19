import unittest
from src.room_parser.building_blocks.door import Door
from src.room_parser.building_blocks.player import Player

from src.room_parser.building_blocks.wall import Wall
from src.room_parser.room_parser import RoomParseException, RoomParser


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

    def test_parse_room_without_player(self):
        room_parser = RoomParser()

        with self.assertRaises(RoomParseException) as error_thrown:
            room_parser.parse(
                """###
                              # |#
                              ###"""
            )

        self.assertEqual(
            str(error_thrown.exception),
            "Invalid Room: @ (player) character is missing.",
        )

    def test_parse_room_without_door(self):
        room_parser = RoomParser()

        with self.assertRaises(RoomParseException) as error_thrown:
            room_parser.parse(
                """###
                              # @#
                              ###"""
            )

        self.assertEqual(
            str(error_thrown.exception),
            "Invalid Room: | (door) character is missing.",
        )

    def test_parse_room_without_mupltiple_players(self):
        room_parser = RoomParser()

        with self.assertRaises(RoomParseException) as error_thrown:
            room_parser.parse(
                """###
                              #@@#
                              #|#"""
            )

        self.assertEqual(
            str(error_thrown.exception),
            "Invalid Room: @ (player) character is present more than once.",
        )
