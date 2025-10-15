
export default class StatsClient {
  constructor() {}
  async getHealth(): Promise<number> {
    const res = await fetch("http://localhost:8002/stats/hp");
    if (!res.ok) {
      throw new Error(`Stats server error: ${res.status}`);
    }
    const body = await res.json();
    return body.hp;
  }
  async hit(): Promise<void> {
    console.log("Hitting stats server");
    const res = await fetch("http://localhost:8002/stats/hp?action=hit", { method: 'POST' });
    if (!res.ok) {
      throw new Error(`Stats server error: ${res.status}`);
    }

  }
  async heal(): Promise<void> {
    const res = await fetch("http://localhost:8002/stats/hp?action=heal", { method: 'POST' });
    if (!res.ok) {
      throw new Error(`Stats server error: ${res.status}`);
    }
  }
}
