import threading
import asyncio
from src import game_service

from src.game.game import Game


async def tick():
    global game
    while True:
        game.tick()
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

    asyncio_thread = threading.Thread(target=run)
    asyncio_thread.daemon = True
    asyncio_thread.start()

    game.mainloop()
