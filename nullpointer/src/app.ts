import express from "express";
import Game from './Game';

async function createApp() {
    const app = express();
    app.use(express.json());
    const game = new Game();

    app.get("/nullpointer", async (_req, res) => {

        res.json({ "layout": game.getCurrentRoom()});

    });

    app.post("/nullpointer", async (req, res) => {
        const { row, column } = req.body;

        game.movePlayer([column, row]);
        res.status(201).json();
    });

    return app;
}

export { createApp };
