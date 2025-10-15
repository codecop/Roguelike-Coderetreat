import { Express } from "express";
import { createApp } from "../src/app";

import request from "supertest";

describe("RoomEndpoints", () => {
  let app: Express;

  beforeEach(async () => {
    app = await createApp();
  });

  it("gets room layout", async () => {
    const response = await request(app).get("/room-ab").expect(200);

    expect(response.header["content-type"]).toBe(
      "application/json; charset=utf-8"
    );
    expect(response.body.layout).toContain("####");
    expect(response.body.description).not.toBeFalsy();
  });

  it("updates the player position", async () => {
    const response = await request(app).post("/room-ab/walk?row=3&column=5").send().expect(201)
    expect(response.body.message).toEqual("Congratulations, you moved.");

    const { body } = await request(app).get("/room-ab");
    expect(body.layout).toContain("#    @  #");
  });
});
