import requests
from src.endpoints.endpoints import Endpoints
from src.room_parser.building_blocks.item import Item


class GameService:
    def __init__(self, endpoints: Endpoints):
        self.endpoints = endpoints

    def get_room(self):
        return self._request(f"{self.endpoints.room_url}")

    def get_stats(self):
        return self._request(f"{self.endpoints.stats_endpoint}/hp")

    def reset_stats(self):
        return self._request(f"{self.endpoints.stats_endpoint}/hp?action=reset", "POST")

    def move(self, column, row):
        return self._request(
            f"{self.endpoints.room_url}/walk?column={column}&row={row}", "POST"
        )

    def act(self, item: Item | None = None):
        item_param = f"item={item.identifier}" if item is not None else "item=c"
        return self._request(f"{self.endpoints.room_url}/interact?{item_param}", "POST")

    def open(self):
        return self._request(f"{self.endpoints.room_url}/open")

    def _request(self, to_endpoint, method="GET", payload=None):
        try:
            request_method = requests.get if method == "GET" else requests.post
            response = request_method(to_endpoint, data=payload)
            if "application/json" not in response.headers.get("Content-Type", ""):
                return None
            return response.json()
        except Exception as e:
            print(str(e))
