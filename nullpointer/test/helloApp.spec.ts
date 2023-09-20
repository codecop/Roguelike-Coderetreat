import { Express } from 'express';
import { createApp } from '../src/app';
// @ts-ignore
import request from 'supertest';

describe('HelloApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('first Game', async () => {
        const response = await request(app).
        get('/nullpointer').
        expect(200);

        const expectedRoom = "###########\n" +
            "#@        #\n" +
            "#         #\n" +
            "#         #\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n";

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe(expectedRoom);
    });

    it('updates player position', async () => {
        await request(app).
        post('/nullpointer/walk?row=3&column=5').
        expect(201);

        const expectedRoom =
            "###########\n" +
            "#         #\n" +
            "#         #\n" +
            "#    @    #\n" +
            "#         |\n" +
            "#         #\n" +
            "###########\n";

        const response = await request(app).
        get('/nullpointer').
        expect(200);

        expect(response.body.layout).toBe(expectedRoom);

    });
});
