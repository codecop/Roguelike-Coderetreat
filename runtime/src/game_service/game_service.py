import requests

from src.game.game import Game


class GameService:
    def __init__(self, game: Game):
        self.game = game

    def get_room(self):
        try:
            json_data = requests.get("http://localhost:8003/1/").json()
            self.game.update_room(json_data)
        except Exception as e:
            print(str(e))

    def get_stats(self):
        try:
            json_data = requests.get("http://localhost:8002/stats/hp").json()
            self.game.update_stats(json_data)
        except Exception as e:
            print(str(e))
