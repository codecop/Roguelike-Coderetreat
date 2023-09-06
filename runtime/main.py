import threading
import requests
import asyncio

from src.game.game import Game


def get_room(*args):
    global game
    try:
        json_data = requests.get("http://localhost:8082/room/").json()
        game.update(json_data["room"])
        print("Updated room...")
    except Exception as e:
        print(str(e))


async def periodic():
    global game
    while True:
        print("Getting room.")
        get_room()
        game.get_stats()
        await asyncio.sleep(1)


def stop():
    task.cancel()


loop = asyncio.get_event_loop()
loop.call_later(5, stop)
task = loop.create_task(periodic())


def run():
    global loop
    try:
        loop.run_until_complete(task)
    except asyncio.CancelledError:
        pass


if __name__ == "__main__":
    game = Game()

    asyncio_thread = threading.Thread(target=run)
    asyncio_thread.daemon = True
    asyncio_thread.start()

    game.mainloop()
