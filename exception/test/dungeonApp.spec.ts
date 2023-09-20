import { Express } from 'express';
import { createApp } from '../src/DungeonApp';
import request from 'supertest';

describe('DungeonApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('DungeonApp should print the initial layout', async () => {
        const response = await request(app).
            get('/exception').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe('#####\n|@  #\n#   #\n#   #\n#####');
    });

    it('updates position', async () => {
        await request(app).
            post('/exception/walk?row=1&column=2').
            expect(201);

        const { body } = await request(app).get('/exception');
        expect(body.layout).toBe('#####\n|   #\n#@  #\n#   #\n#####');
    });

});
