from src.building_blocks.door import Door
from src.building_blocks.player import Player
from src.building_blocks.wall import Wall
from src.building_blocks.empty import Empty


def create_building_block_from_string(raw_string: str):
    raw_to_factory_fn = {
        "#": lambda: Wall(),
        "@": lambda: Player(),
        "|": lambda: Door(),
        " ": lambda: Empty(),
    }
    return raw_to_factory_fn[raw_string]()
