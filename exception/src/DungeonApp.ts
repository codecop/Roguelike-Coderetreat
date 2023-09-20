import express from "express";
import Dungeon from "./Dungeon";

const dungeon = new Dungeon();

async function createApp() {
    const app = express();
    app.use(express.json());

    app.get("/exception", async (_req, res) => {

        res.json({ "layout": dungeon.print() });

    });

    app.post("/exception/walk", async (req, res) => {
        const { row, column } = req.query;
        if (row != undefined && column != undefined) {
            console.log("called", row, column)
            dungeon.setNewPosition(Number(row), Number(column));
            res.status(201).json();
        } else {
            res.status(400).json();
        }

    });

    return app;
}

export { createApp };
