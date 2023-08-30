import { describe, it } from 'mocha';
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
        const body = response.body;
        expect(body.hp).equal(10);
        expect(body.alive).equal(true);
    });

    it('updates HP', async () => {
        await request(app).
            post('/stats/hp').
            query('action=hit').
            expect(201);

        const { body: body1 } = await request(app).get('/stats/hp');
        expect(body1.hp).equal(9);

        await request(app).
            post('/stats/hp').
            query('action=heal').
            expect(201);

        const { body: body2 } = await request(app).get('/stats/hp');
        expect(body2.hp).equal(10);
    });

    it('dynamic attribute level', async () => {
        await request(app).
            get('/stats/level').
            expect(404);

        await request(app).
            post('/stats/level').
            query('action=inc').
            expect(201);

        const { body } = await request(app).get('/stats/level');
        expect(body.level).equal(1);

        await request(app).
            del('/stats/level').
            expect(202);

        await request(app).
            get('/stats/level').
            expect(404);
    });

});
