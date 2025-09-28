from fastapi.testclient import TestClient
from src.app import app


client = TestClient(app)


def test_get_name():
    response = client.get("/hello")
    assert response.status_code == 200
    assert response.headers["content-type"] == "application/json"
    data = response.json()
    assert data["name"] == "World!"


def test_set_name():
    response = client.post("/hello?name=Paul")
    assert response.status_code == 201

    response = client.get("/hello")
    assert response.status_code == 200
    data = response.json()
    assert data["name"] == "Paul"


def test_set_name_missing():
    response = client.post("/hello")
    assert response.status_code == 400
