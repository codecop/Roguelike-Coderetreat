import random


class Endpoints:
    stats_endpoint = "http://localhost:8002/stats"
    rooms_endpoints = []

    def __init__(self, room_endpoints):
        self.rooms_endpoints = room_endpoints
        random.seed(10)
        random.shuffle(self.rooms_endpoints)
        self.current_room_index = 0

    @property
    def room_url(self):
        return self.rooms_endpoints[self.current_room_index]

    def next_room(self):
        rooms_count = len(self.rooms_endpoints)
        if self.current_room_index >= rooms_count - 1:
            exit()
        self.current_room_index = self.current_room_index + 1
