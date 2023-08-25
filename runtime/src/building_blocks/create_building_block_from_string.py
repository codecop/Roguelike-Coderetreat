from src.building_blocks.door import Door
from src.building_blocks.player import Player
from src.building_blocks.wall import Wall


def createBuildingBlockFromString(raw_string: str):
    raw_to_factory_fn = {
        "#": lambda: Wall(),
        "@": lambda: Player(),
        "|": lambda: Door(),
    }
    return raw_to_factory_fn[raw_string]()
