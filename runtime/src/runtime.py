import os
from src.endpoints.endpoints import Endpoints

from src.game.game import Game


def read_endpoints_file():
    script_dir = os.path.dirname(__file__)
    rel_path = "../endpoints.txt"
    abs_file_path = os.path.join(script_dir, rel_path)
    with open(abs_file_path) as file:
        return [line.strip() for line in file.read().split("\n")]


def create_game():
    endpoints_txt = read_endpoints_file()
    game = Game(Endpoints(endpoints_txt))
    return game
