from src.room_parser.building_blocks.create_building_block_from_string import (
    create_building_block_from_string,
)


class RoomParseException(Exception):
    pass


class RoomParser:
    def parse(self, raw_string: str):
        self._check_validity_of_the_room_string(raw_string)

        lines = [line.strip() for line in raw_string.split("\n")]
        non_empty_lines = filter(lambda line: line != "", lines)
        room = []
        for line in non_empty_lines:
            room.append([])
            for character in line:
                room[-1].append(create_building_block_from_string(character))
        return room

    def _check_validity_of_the_room_string(self, raw_string: str) -> bool:
        if "@" not in raw_string:
            raise RoomParseException("Invalid Room: @ (player) character is missing.")

        if raw_string.count("@") > 1:
            raise RoomParseException(
                "Invalid Room: @ (player) character is present more than once."
            )

        if "|" not in raw_string:
            raise RoomParseException("Invalid Room: | (door) character is missing.")
