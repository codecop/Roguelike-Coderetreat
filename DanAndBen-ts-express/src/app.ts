import express from "express";
import Room from "./Room";

const room = new Room(1, 1);

async function createApp() {
    const app = express();
    app.use(express.json());
    
    app.get("/defaultRoom", async (_req, res) => {
        
        res.json({ "layout": room.toString() });
    });

    // app.post("/hello", async (req, res) => {
    //     const { name } = req.body;
    //     if (name != undefined) {
    //         hello.setName(name);
    //         res.status(201).json();
    //     } else {
    //         res.status(400).json();
    //     }
    //
    // });

    return app;
}

export { createApp };
