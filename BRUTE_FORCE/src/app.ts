import express from "express";
import Hello from "./Hello";
import Room from "./Room";

const room = new Room();

const hello = new Hello();

async function createApp() {
  const app = express();
  app.use(express.json());

  app.get("/hello", async (_req, res) => {
    res.json({ name: hello.getName() });
  });
  app.get("/room", async (_req, res) => {
    res.json({ layout: room.returnLayout() });
  });

app.post("/room/walk", async (req, res) => {
    const row = req.query.row;
    const column = req.query.column;
    if (row !== undefined && column !== undefined) {
      room.setNewPosition(Number(row), Number(column));
      res.status(201).json();
    } else {
      res.status(400).json();
    }
  });

  app.post("/hello", async (req, res) => {
    const name = req.query.name;
    if (name != undefined) {
      hello.setName(name.toString());
      res.status(201).json();
    } else {
      res.status(400).json();
    }
  });

  return app;
}

export { createApp };
