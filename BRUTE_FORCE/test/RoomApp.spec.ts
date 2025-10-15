import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';

describe('RoomApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

       it('should get room', async () => {
        const response = await request(app).
            get('/room').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe('######\n#@   #\n#    |\n#    #\n######');
    });

    it('should update position', async () => {
        await request(app).
            post('/room/walk?row=2&column=2').
            send().
            expect(201);

        const { body } = await request(app).get('/room');
        expect(body.layout).toBe('######\n#    #\n# @  |\n#    #\n######');
    });
});
