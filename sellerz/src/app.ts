import express from 'express';
import { Room } from './room';

async function createApp() {
  const app = express();
  app.use(express.json());
  const room = new Room();

  app.get('/sellerz', async (_req, res) => {
    const layout = room.getMap();

    res.json({ layout });
  });

  app.post('/sellerz/walk', async (req, res) => {
    const { row, column } = req.query;
    room.setNewPosition(Number(row), Number(column));

    res.status(201).json();
  });

  return app;
}

export { createApp };
