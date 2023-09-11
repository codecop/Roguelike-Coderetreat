import requests
from src.endpoint_provider.endpoint_provider import EndpointProvider

from src.game.game import Game


class GameService:
    def __init__(self, game: Game):
        self.game = game

    def get_room(self):
        try:
            json_data = requests.get(EndpointProvider().rooms_endpoints[0]).json()
            self.game.update_room(json_data)
        except Exception as e:
            print(str(e))

    def get_stats(self):
        try:
            json_data = requests.get(EndpointProvider().stats_endpoint + "/hp").json()
            self.game.update_stats(json_data)
        except Exception as e:
            print(str(e))
