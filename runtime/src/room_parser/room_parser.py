from src.room_parser.building_blocks.create_building_block_from_string import (
    create_building_block_from_string,
)


class RoomParser:
    def parse(self, raw_string: str):
        lines = [line.strip() for line in raw_string.split("\n")]
        non_empty_lines = filter(lambda line: line != "", lines)
        room = []
        for line in non_empty_lines:
            room.append([])
            for character in line:
                room[-1].append(create_building_block_from_string(character))
        return room
