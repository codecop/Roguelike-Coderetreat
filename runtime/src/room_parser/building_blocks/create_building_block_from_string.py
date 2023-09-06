from src.room_parser.building_blocks.door import Door
from src.room_parser.building_blocks.item import Item
from src.room_parser.building_blocks.player import Player
from src.room_parser.building_blocks.wall import Wall
from src.room_parser.building_blocks.empty import Empty


def create_building_block_from_string(raw_string: str):
    raw_to_factory_fn = {
        "#": lambda: Wall(),
        "@": lambda: Player(),
        "|": lambda: Door(),
        " ": lambda: Empty(),
    }
    default_fn = lambda: Item(raw_string)
    factory_fn = raw_to_factory_fn.get(raw_string, default_fn)
    return factory_fn()
