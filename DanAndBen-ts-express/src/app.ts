import express from "express";
import Room from "./Room";

const room = new Room(1, 1);

async function createApp() {
    const app = express();
    app.use(express.json());
    
    app.get("/defaultRoom", async (_req, res) => {
        
        res.json({ "layout": room.toString() });
    });

    app.post("/defaultRoom/walk", async (req, res) => {
        
        let row: number = parseInt(req.query.row as string);
        let column: number = parseInt(req.query.column as string);
        
        if (row && column) {
            room.setNewPlayerPosition(column, row);
            res.status(201).json();
        } else {
            res.status(400).json();
        }

    });

    return app;
}

export { createApp };
