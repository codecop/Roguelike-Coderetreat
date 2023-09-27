from flask import Flask, request, jsonify, abort
from src.hello import Hello

app = Flask(__name__)

hello = Hello()

@app.route('/hello', methods=['GET'])
def get_data():
    global hello
    return jsonify({ "name": hello.getName() })

@app.route('/hello', methods=['POST'])
def add_data():
    global hello
    new_name = request.json
    if len(new_name) > 0:
        hello.setName(new_name)
        return jsonify({ "message": "Item added successfully!" }), 201
    else:
        return abort(400, "no name specified")

if __name__ == '__main__':
    app.run(debug=True, port=5000)
