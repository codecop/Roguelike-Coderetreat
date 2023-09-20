import express from "express";
import Game from './Game';

async function createApp() {
    const app = express();
    app.use(express.json());
    const game = new Game();

    app.get("/nullpointer", async (_req, res) => {

        res.json({ "layout": game.getCurrentRoom()});

    });

    app.post("/nullpointer/walk", async (req, res) => {
        let { row, column } = req.query;

        // @ts-ignore
        row = parseInt(row);
        // @ts-ignore
        column = parseInt(column);

        game.movePlayer([column, row]);
        res.status(201).json();
    });

    return app;
}

export { createApp };
