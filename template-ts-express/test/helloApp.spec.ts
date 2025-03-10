import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';

describe('HelloApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('first Hello', async () => {
        const response = await request(app).
            get('/hello').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.name).toBe('World!');
    });

    it('updates', async () => {
        await request(app).
            post('/hello?name=Peter').
            send().
            expect(201);

        const { body } = await request(app).get('/hello');
        expect(body.name).toBe('Peter');
    });

});
