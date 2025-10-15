import express from 'express';
import { Room } from './room';

async function createApp() {
  const app = express();
  app.use(express.json());
  const room = new Room();

  app.get('/sellerz', async (_req, res) => {
    const layout = room.getMap();
    const description = room.getDescription();

    res.json({ layout, description });
  });

  app.post('/sellerz/walk', async (req, res) => {
    const { row, column } = req.query;
    const message = room.setNewPosition(Number(row), Number(column));

    res.status(201).json({ message });
  });

  app.get('/sellerz/open', async (_req, res) => {
    res.json(room.getOpenDoors());
  });

  return app;
}

export { createApp };
