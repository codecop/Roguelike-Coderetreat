import pytest
from src.app import app
import json

@pytest.fixture
def client():
    # Create a test client using your Flask app
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

def read_response(response):
    str_response = response.data.decode('utf-8')
    return json.loads(str_response)

def test_get_name(client):
    response = client.get("/hello")
    data = read_response(response)
    assert "World!" == data["name"]

def test_set_name(client):
    client.post("/hello", json="Paul")

    response = client.get("/hello")
    data = read_response(response)
    assert "Paul" == data["name"]
