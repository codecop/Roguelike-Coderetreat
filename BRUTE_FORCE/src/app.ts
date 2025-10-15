import express from "express";
import Room from "./Room";

const room = new Room();

async function createApp(statsClient) {
  const app = express();

  app.use(express.json());

  app.get("/room", async (_req, res) => {
    res.json({ layout: room.returnLayout(), description: "Beware! There is a fire in the room. Do not approach it, or you will be burned and lose 1 HP for every move you make within its area. The fire only affects squares that are directly above, below, left, or right of it—not those that touch it diagonally." });
  });

  app.get("/stats/hp", async (_req, res) => {
    const hp = await statsClient.getHealth();
    res.json({ hp });
  });
  app.post("/stats/hp", async (req, res) => {
    const action = req.query.action;
    if (action === "hit") {
      await statsClient.hit();
      res.status(201).json();
    } else if (action === "heal") {
      await statsClient.heal();
      res.status(201).json();
    } else {
      res.status(400).json();
    }
  });

  app.post("/room/walk", async (req, res) => {
    const row = req.query.row;
    const column = req.query.column;
    if (row !== undefined && column !== undefined) {
      room.setNewPosition(Number(row), Number(column));
      await room.hurtIfNearFire(Number(row), Number(column));
      res.status(201).json();
    } else {
      res.status(400).json();
    }
  });

  return app;
}

export { createApp };
