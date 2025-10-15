import request from 'supertest';
export default class StatsClient {
  constructor() {}
  async getHealth(): Promise<number> {
    const response = await request("http://localhost:8002")
      .get("/stats/hp")
      .expect(200);
    const { body } = response;
    return body.hp;
  }
}
