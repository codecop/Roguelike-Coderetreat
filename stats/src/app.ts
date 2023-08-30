import express from "express";
import Stats from "./Stats";

const stats = new Stats();

async function createApp() {
    const app = express();
    app.use(express.json());

    app.get("/stats", async (_req, res) => {
        const html = `
        <html lang="en">
        <body>
          <p><a href="/stats/hp">HP</a></p>
        </body>
        </html>
        `;

        res.send(html);
    });

    app.get("/stats/hp", async (_req, res) => {

        res.json({ hp: stats.getHp(), alive: stats.alive() });

    });

    app.post("/stats/hp", async (req, res) => {
        const action = req.query.action;
        if (action === 'reset') {
            stats.resetHp();
            res.status(201).json();
        } else if (action === 'hit') {
            stats.takeDamage();
            res.status(201).json();
        } if (action === 'heal') {
            stats.heal();
            res.status(201).json();
        } else {
            res.status(400).json();
        }

    });

    return app;
}

export { createApp };
