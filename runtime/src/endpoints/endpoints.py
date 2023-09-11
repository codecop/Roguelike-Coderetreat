class Endpoints:
    stats_endpoint = "http://localhost:8002/stats"
    rooms_endpoints = ["http://localhost:8004/key"]

    def __init__(self):
        self.current_room_index = 0

    @property
    def room_url(self):
        return self.rooms_endpoints[self.current_room_index]

    def next_room(self):
        rooms_count = len(self.rooms_endpoints)
        self.current_room_index = (self.current_room_index + 1) % rooms_count
