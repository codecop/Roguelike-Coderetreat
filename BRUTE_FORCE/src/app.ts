import express from "express";

import Room from "./Room";

const room = new Room();

async function createApp() {
    
  const app = express();

  app.use(express.json());

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

  return app;
}

export { createApp };
