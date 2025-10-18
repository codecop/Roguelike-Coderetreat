import threading
import asyncio

from src.runtime import create_game


async def tick():
    global game
    while True:
        game.tick()
        await asyncio.sleep(0.2)


def run():
    global loop
    try:
        loop.run_forever()
    except asyncio.CancelledError:
        pass


if __name__ == "__main__":
    try:
        # For Python 3.10+, use new asyncio policy
        if hasattr(asyncio, 'get_running_loop'):
            loop = asyncio.get_running_loop()
        else:
            # Fallback for older Python versions
            loop = asyncio.get_event_loop()
    except RuntimeError:
        loop = asyncio.new_event_loop()
        asyncio.set_event_loop(loop)
    
    loop.create_task(tick())

    game = create_game()

    asyncio_thread = threading.Thread(target=run)
    asyncio_thread.daemon = True
    asyncio_thread.start()

    game.start()
