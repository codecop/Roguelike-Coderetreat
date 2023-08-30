import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';
import { expect } from 'chai';

describe('Stats App', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('show HP', async () => {
        const response = await request(app).
            get('/stats/hp').
            expect(200);

        expect(response.header['content-type']).equal('application/json; charset=utf-8');
        expect(response.body.hp).equal(100);
    });

    it('updates', async () => {
        await request(app).
            post('/stats/hp').
            query('action=hit').
            expect(201);

        const { body: body1 } = await request(app).get('/stats/hp');
        expect(body1.hp).equal(99);

        await request(app).
            post('/stats/hp').
            query('action=heal').
            expect(201);

        const { body: body2 } = await request(app).get('/stats/hp');
        expect(body2.hp).equal(100);
    });

});
