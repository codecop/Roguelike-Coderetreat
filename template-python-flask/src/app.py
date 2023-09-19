from flask import Flask, request, jsonify, abort

app = Flask(__name__)

name = "World!"

@app.route('/hello', methods=['GET'])
def get_data():
    return jsonify({"name":name})

@app.route('/hello', methods=['POST'])
def add_data():
    global name
    new_name = request.json
    if len(new_name) > 0:
        name = new_name
        return jsonify({"message": "Item added successfully!"})
    else:
        return abort(400, "no name specified")

if __name__ == '__main__':
    app.run(debug=True)
