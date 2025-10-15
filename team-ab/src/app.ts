import express from "express";
import Room from "./Room";

const room = new Room(7, 6);
room.generateLayout();
room.setNewPlayerPosition(1, 1);

async function createApp() {
  const app = express();
  app.use(express.json());

  app.get("/room-ab", async (_req, res) => {
    res.json({
      layout: room.layout,
      description: "This is Room AB, created by Andrej and Ben in TS. Its a cool room full of mysteries. There is a door to the east.",
    });
  });

  app.post("/room-ab/walk", async (req, res) => {
    const row = req.query.row;
    const column = req.query.column;

    if (row != undefined && column != undefined) {
      room.setNewPlayerPosition(Number(column), Number(row));
      res.status(201).json({ message: "Congratulations, you moved."});
    } else {
      res.status(400).json();
    }
  });

  return app;
}

export { createApp };
