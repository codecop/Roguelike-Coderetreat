import express from "express";
import Stats from "./Stats";

const stats = new Stats();

async function createApp() {
    const app = express();
    app.use(express.json());
    app.use(express.urlencoded({ extended: true }));

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

    app.get("/stats/:id", async (req, res) => {
        const id = req.params.id;
        if (id === 'hp') {

            res.json({ hp: stats.getHp(), alive: stats.alive() });

        } else {

            const result: any = {};
            result[id] = stats.getDynamic(id);
            res.json(result);

        }

    });

    app.post("/stats/:id", async (req, res) => {
        const id = req.params.id;
        const action = req.query.action;

        if (id === 'hp') {

            if (action === 'reset') {
                stats.resetHp();
                res.status(202).json();

            } else if (action === 'hit') {
                stats.takeDamage();
                res.status(201).json();

            } if (action === 'heal') {
                stats.heal();
                res.status(201).json();

            } else {
                res.status(400).json();
            }

        } else {

            if (action === 'dec') {
                stats.decDynamic(id);
                res.status(201).json();

            } if (action === 'inc') {
                stats.incDynamic(id);
                res.status(201).json();

            } else {
                res.status(400).json();
            }

        }
    });

    app.delete("/stats/:id", async (req, res) => {
        const id = req.params.id;

        if (id === 'hp') {
            stats.resetHp();
            res.status(202).json();

        } else {
            stats.resetDynamic(id);
            res.status(202).json();
        }
    });

    return app;
}

export { createApp };
