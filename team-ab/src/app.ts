import express from "express";
import Room from "./Room";

const room = new Room(7, 6);
room.generateLayout();

async function createApp() {
  const app = express();
  app.use(express.json());

  app.get("/room-ab", async (_req, res) => {
    res.json({
      layout: room.layout,
    });
  });

  app.post("/room-ab/walk", async (req, res) => {
    const row = req.query.row;
    const column = req.query.column;

    if (row != undefined && column != undefined) {
      room.setNewPlayerPosition(Number(column), Number(row));
      res.status(201).json();
    } else {
      res.status(400).json();
    }
  });

  return app;
}

export { createApp };
