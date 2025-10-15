import { Express } from 'express';
import { createApp } from '../src/app';
import StatsClient from '../src/statsClient';
import request from 'supertest';

describe('RoomApp', () => {

    let app: Express;
    
    beforeEach(async () => {
        const statsClient = new StatsClient();
        app = await createApp(statsClient);
        jest.restoreAllMocks();
    });

       it('should get room', async () => {
        const response = await request(app).
            get('/room').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe("######\n#@   #\n#  f |\n#    #\n######");
    });

    it('should update position', async () => {
        await request(app).
            post('/room/walk?row=2&column=2').
            send().
            expect(201);

        const { body } = await request(app).get('/room');
        expect(body.layout).toBe("######\n#    #\n# @f |\n#    #\n######");
    });

    
    it.skip("should get the health status", async () => {
        const response = await request('http://localhost:8002').
            get('/stats/hp').
            expect(200);
        const { body } = response;
        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(body.hp).toBe(10);
    });
});
