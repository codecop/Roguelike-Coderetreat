import express from "express";
import Hello from "./Hello";

const hello = new Hello();

async function createApp() {
    const app = express();
    app.use(express.json());

    app.get("/hello", async (_req, res) => {

        res.json({ "name": hello.getName() });

    });

    app.post("/hello", async (req, res) => {
        const { name } = req.body;
        if (name != undefined) {
            hello.setName(name);
            res.status(201).json();
        } else {
            res.status(400).json();
        }

    });

    return app;
}

export { createApp };
