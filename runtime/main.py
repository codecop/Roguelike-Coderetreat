import threading
import asyncio
from src import game_service

from src.game.game import Game
from test.test_game_service import GameService


async def tick():
    global game_service
    while True:
        game_service.get_room()
        game_service.get_stats()
        await asyncio.sleep(0.5)


def run():
    global loop
    try:
        loop.run_forever()
    except asyncio.CancelledError:
        pass


if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.create_task(tick())

    game = Game()
    game_service = GameService(game)

    asyncio_thread = threading.Thread(target=run)
    asyncio_thread.daemon = True
    asyncio_thread.start()

    game.mainloop()
