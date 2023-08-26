import threading
import click
from flask import Flask, request, jsonify

from src.game.game import Game

app = Flask(__name__)


@app.route("/input", methods=["POST"])
def input_handler():
    global game
    try:
        json_data = request.get_json()
        game.update(json_data["room"])
        return jsonify({"result": json_data}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@click.command()
@click.option("--host", default="127.0.0.1", help="Host address to bind to.")
@click.option("--port", default=5000, help="Port to listen on.")
def run_server(host, port):
    global app
    app.run(host=host, port=port)


if __name__ == "__main__":
    web_server_thread = threading.Thread(target=run_server)
    web_server_thread.daemon = True
    web_server_thread.start()

    game = Game()
    game.mainloop()
