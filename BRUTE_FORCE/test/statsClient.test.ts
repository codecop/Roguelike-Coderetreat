import { Express } from 'express';
import { createApp } from '../src/app';
import StatsClient from '../src/statsClient';

describe('RoomApp', () => {

    let app: Express;
    let statsClient: StatsClient;
    beforeEach(async () => {
        statsClient = new StatsClient();
        jest.spyOn(statsClient, 'getHealth').mockResolvedValue(10);
        app = await createApp(statsClient);
        jest.restoreAllMocks();
    });

    it('should get health status', async () => {
        const response = await require('supertest')(app)
            .get('/stats/hp')
            .expect(200);

        const { body } = response;
        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(body.hp).toBe(10);
    });
});
