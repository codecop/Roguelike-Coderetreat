import { Express } from "express";
import { createApp } from "../src/app";
import StatsClient from "../src/statsClient";

describe("RoomApp", () => {
  let app: Express;
  let statsClient: StatsClient;
  beforeEach(async () => {
    statsClient = new StatsClient();
    app = await createApp(statsClient);
  });
  afterEach(() => {
    jest.restoreAllMocks();
  });

  it("should get health status", async () => {
    jest.spyOn(statsClient, "getHealth").mockResolvedValue(10);
    const response = await require("supertest")(app)
      .get("/stats/hp")
      .expect(200);

    const { body } = response;
    expect(response.header["content-type"]).toBe(
      "application/json; charset=utf-8"
    );
    expect(body.hp).toBe(10);
  });
  it("should hit and reduce health by 1", async () => {
    jest.spyOn(statsClient, "getHealth").mockResolvedValueOnce(9);
    jest.spyOn(statsClient, "hit").mockResolvedValue();

    await require("supertest")(app).post("/stats/hp?action=hit").expect(201);

    const response = await require("supertest")(app)
      .get("/stats/hp")
      .expect(200);

    const { body } = response;
    expect(response.header["content-type"]).toBe(
      "application/json; charset=utf-8"
    );
    expect(body.hp).toBe(9);
  });
  it("should heal and increase health by 1", async () => {
    jest.spyOn(statsClient, "getHealth").mockResolvedValueOnce(11);
    jest.spyOn(statsClient, "heal").mockResolvedValue();
    await require("supertest")(app).post("/stats/hp?action=heal").expect(201);

    const response = await require("supertest")(app)
      .get("/stats/hp")
      .expect(200);

    const { body } = response;
    expect(response.header["content-type"]).toBe(
      "application/json; charset=utf-8"
    );
    expect(body.hp).toBe(11);
  });
});
